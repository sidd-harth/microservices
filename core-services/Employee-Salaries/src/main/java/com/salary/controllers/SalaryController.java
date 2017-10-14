package com.salary.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.salary.dao.SalaryDAO;
import com.salary.model.Salary;

@RestController
public class SalaryController {

	private static final Logger LOG = Logger.getLogger(SalaryController.class.getName());

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Autowired
	private SalaryDAO salaryDAO;

	@RequestMapping(value = "/salaries", method = RequestMethod.GET)
	public List<Salary> getSalaries() {
		LOG.info("Inside get Salaries");

		return salaryDAO.list();
	}

	@RequestMapping("/salary/{id}")
	@HystrixCommand(
			fallbackMethod = "dummyRecord", //fallback method name
			commandKey="getSalaryById", // setting the name of the actual method which will be displayed in dashbaord
			groupKey="Salary-Details", // the runtime class name of annotated method
			commandProperties= {
					@HystrixProperty (name="circuitBreaker.sleepWindowInMilliseconds", value="5000"),   // wait for 1min when circuit is OPEN & then send a request to see if circuit is working & then closes circuit
					@HystrixProperty (name="circuitBreaker.errorThresholdPercentage", value="90") // doesnt has number of calls...it works in terms of percentages!!
			})
	public ResponseEntity<Salary> getSalary(@PathVariable("id") int id) {
		LOG.info("Salary ID is " + id);
		Salary salary = salaryDAO.get(id);

			if (salary == null) {
				//return new ResponseEntity("No Employee found for ID " + id, HttpStatus.NOT_FOUND);
				LOG.warn("No Employee found for ID " + id);
				throw new RuntimeException();
		}

		return new ResponseEntity<Salary>(salary, HttpStatus.OK);
	}

	//Fallback Method
		public ResponseEntity<Salary> dummyRecord(@PathVariable("id") int id){
			
			Salary salary =  salaryDAO.get(id);
			if (salary == null) {
				LOG.warn("Inside Fallback Method");
				//return new ResponseEntity("No Employee found for ID " + id, HttpStatus.NOT_FOUND);
				LOG.warn("No Salary found for ID " + id);
				
				return new ResponseEntity("{\"id\":"+ id + ", \"salary\":\"Fallback Salary\"}", HttpStatus.OK);
				
			}
			return new ResponseEntity<Salary>(salary, HttpStatus.OK);
		}
	
	@RequestMapping("/salary/zipkin")
	public String zipkinCall() throws RestClientException, IOException {

		ServiceInstance serviceInstance = loadBalancer.choose("employee-details");
		LOG.info(serviceInstance.getUri());
		String empUrl = serviceInstance.getUri().toString();
		empUrl = empUrl + "/employees/zipkin";

		// String empUrl = "http://localhost:8081/employees/zipkin";

		//RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(empUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		

		LOG.info(response.getBody());
		LOG.info("Successfull...Check Zipkin for Details");
		return "Successfull...Check Zipkin for Details";
	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}