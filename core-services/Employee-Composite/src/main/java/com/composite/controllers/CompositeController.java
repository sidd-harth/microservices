package com.composite.controllers;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class CompositeController {

	private static final Logger LOG = Logger.getLogger(CompositeController.class.getName());

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/composite/{id}", method = RequestMethod.GET)
	public String getMashup(@PathVariable("id") int id) throws RestClientException, IOException {

		// String salUrl = "http://localhost:8082/salary/1";
		// String empUrl = "http://localhost:8081/employees/1";
		// String ratUrl = "http://localhost:8083/ratings/1";
		// Salary Servvice
		LOG.info("Inside Salary Service");
		ServiceInstance serviceInstance = loadBalancer.choose("employee-salary");
		LOG.info(serviceInstance.getUri());
		String salUrl = serviceInstance.getUri().toString();
		salUrl = salUrl + "/salary/" + id;

		// RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(salUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		LOG.info(response.getBody());

		String salBody = response.getBody();

		// Employee Servvice
		LOG.info("Inside Employee Service");
		ServiceInstance serviceInstance2 = loadBalancer.choose("employee-details");
		LOG.info(serviceInstance2.getUri());
		String empUrl = serviceInstance2.getUri().toString();
		empUrl = empUrl + "/employees/" + id;

		LOG.info("Called URL is " + empUrl);
		// RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response2 = null;

		try {
			response2 = restTemplate.exchange(empUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		LOG.info(response2.getBody());
		String empBody = response2.getBody();

		// Rating Servvice
		LOG.info("Inside Rating Service Service");
		ServiceInstance serviceInstance3 = loadBalancer.choose("employee-rating");
		LOG.info(serviceInstance3.getUri());
		String ratUrl = serviceInstance3.getUri().toString();
		ratUrl = ratUrl + "/ratings/" + id;

		// RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response3 = null;

		try {
			response = restTemplate.exchange(ratUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}

		LOG.info(response.getBody());
		String ratBody = response.getBody();
		// JSONObject myObject = new JSONObject("ratBody");
		// return(myObject)

		return ("[" + salBody + "," + empBody + "," + ratBody + "]");
	}

	@RequestMapping(value = "/zipkin-test", method = RequestMethod.GET)
	public String zipkinCall() throws RestClientException, IOException {

		ServiceInstance serviceInstance = loadBalancer.choose("employee-salary");
		LOG.info(serviceInstance.getUri());
		String empUrl = serviceInstance.getUri().toString();
		empUrl = empUrl + "/salary/zipkin";

		// String empUrl = "http://localhost:8082/salary/zipkin";

		// RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			response = restTemplate.exchange(empUrl, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		ServiceInstance serviceInstance2 = loadBalancer.choose("employee-rating");
		LOG.info(serviceInstance.getUri());
		String empUrl2 = serviceInstance.getUri().toString();
		empUrl2 = empUrl2 + "/ratings/zipkin";

		// String empUrl = "http://localhost:8081/employees/zipkin";

		// RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response2 = null;

		try {
			response2 = restTemplate.exchange(empUrl2, HttpMethod.GET, getHeaders(), String.class);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		System.out.println(response.getBody());
		String empBody = response.getBody();
		LOG.info("Successfull...Check Zipkin for Details");
		return ("Successfull...Check Zipkin for Details");

	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}