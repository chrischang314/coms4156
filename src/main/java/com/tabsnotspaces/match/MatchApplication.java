package com.tabsnotspaces.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	public static void main(String[] args) {
		SpringApplication.run(MatchApplication.class, args);
	}

}
