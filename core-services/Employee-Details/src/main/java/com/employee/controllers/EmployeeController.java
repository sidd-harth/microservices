package com.employee.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class EmployeeController {

	private static final Logger LOG = Logger.getLogger(EmployeeController.class.getName());

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	private EmployeeDAO EmployeeDAO;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/employees", method = RequestMethod.GET)
	public List<Employee> getEmployees() {
		LOG.info("Inside get employees");

		return EmployeeDAO.list();
	}

	@RequestMapping("/employees/{id}")
	@HystrixCommand(
			fallbackMethod = "dummyRecord", //fallback method name
			commandKey="getEmployeeById", // setting the name of the actual method which will be displayed in dashbaord
			groupKey="Employee-Details", // the runtime class name of annotated method
			commandProperties= {
					@HystrixProperty (name="circuitBreaker.sleepWindowInMilliseconds", value="5000"),   // wait for 1min when circuit is OPEN & then send a request to see if circuit is working & then closes circuit
					@HystrixProperty (name="circuitBreaker.errorThresholdPercentage", value="90") // doesnt has number of calls...it works in terms of percentages!!
			})
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id) {

		Employee employee = EmployeeDAO.get(id);
		LOG.info("Employee ID is " + id);
		if (employee == null) {
			//return new ResponseEntity("No Employee found for ID " + id, HttpStatus.NOT_FOUND);
			LOG.warn("No Employee found for ID " + id);
			throw new RuntimeException();
			
		}

		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}
	
	//Fallback Method
	public ResponseEntity<Employee> dummyRecord(@PathVariable("id") int id){
		
		Employee employee =  EmployeeDAO.get(id);
		if (employee == null) {
			LOG.warn("Inside Fallback Method");
			//return new ResponseEntity("No Employee found for ID " + id, HttpStatus.NOT_FOUND);
			LOG.warn("No Employee found for ID " + id);
			
			return new ResponseEntity("{\"id\":"+ id + ", \"name\":\"Fallback Name\"}", HttpStatus.OK);
			
		}
		return new ResponseEntity<Employee>(employee, HttpStatus.OK);
	}

	@RequestMapping("/employees/zipkin")
	public String zipkinCall() throws RestClientException, IOException {

		ServiceInstance serviceInstance = loadBalancer.choose("employee-rating");
		LOG.info(serviceInstance.getUri());
		String empUrl = serviceInstance.getUri().toString();
		empUrl = empUrl + "/ratings/zipkin";

		// String empUrl = "http://localhost:8083/ratings/zipkin";
		// RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(empUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		System.out.println(response.getBody());
		LOG.info("Successfull...Check Zipkin for Details");
		return "Successfull...Check Zipkin for Details";
	}
	
	//This Class & Method is to demo Spring Cloud @Refresh feature
	@RestController
    @RefreshScope
    class ConfigExample {

        @Value("${name}")
        private String name;
        
        @Value("${id}")
        private String id;
       
        @Value("${city}")
        private String city;
        
        @Value("${language}")
        private String language; 
        

        @RequestMapping(value = "/employees/67", method = RequestMethod.GET)
        public String sayValue() {
        	//return(id + name + city + language);
        	return("{\"id\":\""+ id + "\", \"name\":\""+ name + "\", \"city\":\"" + city+ "\", \"language\":\"" + language +"\"}");
            //return "Name - "+ emp_name+ "| ID - " +emp_id+ "| Desgination - " +emp_designation+ "| Salary - " +emp_salary;
        }
	} 

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}