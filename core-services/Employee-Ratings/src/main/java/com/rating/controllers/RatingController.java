package com.rating.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rating.dao.RatingDAO;
import com.rating.model.Rating;

@RestController
public class RatingController {

	private static final Logger LOG = Logger.getLogger(RatingController.class.getName());

	@Autowired
	private RatingDAO ratingsDAO;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/ratings", method = RequestMethod.GET)
	public List<Rating> getRatings() {
		LOG.info("Inside get Ratings");

		return ratingsDAO.list();
	}

	@RequestMapping("/ratings/{id}")
	@HystrixCommand(fallbackMethod = "dummyRecord", // fallback method name
			commandKey = "getRatingById", // setting the name of the actual method which will be displayed in dashbaord
			groupKey = "Rating-Details", // the runtime class name of annotated method
			commandProperties = { @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"), 
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "90") 																										
			})
	public ResponseEntity<Rating> getRating(@PathVariable("id") int id) {

		Rating rating = ratingsDAO.get(id);
		LOG.info("Rating ID is " + id);
		if (rating == null) {
			// return new ResponseEntity("No Employee found for ID " + id,
			// HttpStatus.NOT_FOUND);
			LOG.warn("No Rating found for ID " + id);
			throw new RuntimeException();
		}

		return new ResponseEntity<Rating>(rating, HttpStatus.OK);
	}

	// Fallback Method
	public ResponseEntity<Rating> dummyRecord(@PathVariable("id") int id) {

		Rating rating = ratingsDAO.get(id);
		if (rating == null) {
			LOG.warn("Inside Fallback Method");
			// return new ResponseEntity("No Employee found for ID " + id,
			// HttpStatus.NOT_FOUND);
			LOG.warn("No Employee found for ID " + id);

			return new ResponseEntity("{\"id\":" + id + ", \"rating\":\"Fallback Rating\"}", HttpStatus.OK);

		}
		return new ResponseEntity<Rating>(rating, HttpStatus.OK);
	}

	@RequestMapping("/ratings/zipkin")
	public String zipkinCall() {

		LOG.info("Successfull...Check Zipkin for Details");
		return "Successfull...Check Zipkin for Details";
	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

}