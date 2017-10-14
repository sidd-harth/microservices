package com.composite;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@EnableHystrixDashboard
@EnableCircuitBreaker
@SpringBootApplication
@EnableEurekaClient
public class CompositeService {

	public static void main(String[] args) {
		SpringApplication.run(CompositeService.class, args);
	}
	
	

}