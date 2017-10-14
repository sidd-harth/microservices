package com.composite;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.composite.controllers")
public class SleuthConfigurationClass {


	@Bean
	public AlwaysSampler defaultSampler() {
	  return new AlwaysSampler();
	}
	
	}