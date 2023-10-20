package com.tabsnotspaces.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class represents the entry point of the Match Application.
 */
@SpringBootApplication
public class MatchApplication {
	
	@Autowired
	ClientRepository repository;
	
	@Autowired
	ConsumerRepository consumerRepository;
	
	@Autowired
	ServiceProviderRepository serviceProviderRepository;
	
	@Autowired
	ConsumerRequestRepository consumerRequestRepository;
	
	@Autowired
	AppointmentRepository appointmentRepository;

	/**
	 * The main method to start the Spring Boot application.
	 *
	 * @param args Command-line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MatchApplication.class, args);
	}

}
