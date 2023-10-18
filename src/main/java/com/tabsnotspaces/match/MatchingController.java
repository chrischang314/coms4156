package com.tabsnotspaces.match;

import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;


@RestController
public class MatchingController {
	
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

	
	@GetMapping("/client/{id}")
	public Client client(@PathVariable Long id) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) {
			client = clientOpt.get();
		}
		// https://stackoverflow.com/questions/50904742/property-or-field-name-cannot-be-found-on-object-of-type-java-util-optional
		return client;
	}
	
	// https://stackoverflow.com/questions/57184276/the-method-findonelong-is-undefined-for-the-type-personrepository
	
	@GetMapping("/clients")
	public Iterable<Client> clientsList(){
		return repository.findAll();
	}
	
	// TODO change to @RequestBody Client client as input
	@PostMapping("/clients")
	public Client clientsAdd(@RequestParam String name, Model model){
		Client client = new Client();
		client.setClientName(name);
		
		return repository.save(client);
	}


	@DeleteMapping("/client/{id}")
	void deleteClient(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@PostMapping("/client/{id}/consumer")
	public Consumer consumerAdd(@PathVariable Long id,
			@RequestBody Consumer consumer){
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) { // TODO report error otherwise
			client = clientOpt.get();
			// TODO check if consumer is present
			
			Consumer createdConsumer = consumerRepository.save(consumer);
			client.getConsumers().add(consumer);
			repository.save(client);
			
			return createdConsumer;
		}

		return consumer;
	}
	
	@PostMapping("/client/{id}/serviceProvider")
	public ServiceProvider serviceProviderAdd(@PathVariable Long id, @RequestBody ServiceProvider serviceProvider){
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) { // TODO report error otherwise
			client = clientOpt.get();
			// TODO check if service provider is present			
			ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
			client.getServiceProviders().add(serviceProvider);
			repository.save(client);
			
			return createdServiceProvider;
		}

		return serviceProvider;
	}

	/*
	 * @PostMapping("/client/{id}/addAvailability") public String
	 * serviceProviderAdd(@PathVariable Long id,
	 * 
	 * @RequestParam String providerId, // TODO change to long, for the REST API
	 * 
	 * @RequestParam String availableDate, Model model){ Optional<Client> clientOpt
	 * = repository.findById(id); Client client = null; if(clientOpt.isPresent()) {
	 * client = clientOpt.get(); Optional<ServiceProvider> sP =
	 * serviceProviderRepository.findById(Long.parseLong(providerId)); // check if
	 * service provider is present if(sP.isPresent()) { ServiceProvider
	 * serviceProvider = sP.get(); SimpleDateFormat readingFormat = new
	 * SimpleDateFormat(availableDate); serviceProvider.setAvailability(null);
	 * 
	 * serviceProviderRepository.save(serviceProvider); model.addAttribute("client",
	 * client); return "redirect:/client/" + id; } // TODO report error otherwise
	 * model.addAttribute("clients", repository.findAll()); return "clients"; }
	 * 
	 * // TODO report error otherwise model.addAttribute("clients",
	 * repository.findAll()); return "clients"; }
	 */	
	
	@PostMapping("/client/{id}/consumerRequest")
	public ConsumerRequest consumerAdd(@PathVariable Long id, @RequestBody ConsumerRequest consumerRequest){
		Optional<Client> clientOpt = repository.findById(id);
		if(clientOpt.isPresent()) { // TODO report error otherwise
			//  TODO check if consumer request is present
			ConsumerRequest createdConsumerRequest = consumerRequestRepository.save(consumerRequest);
			
			long consumerId = consumerRequest.getConsumerId();
			//  check if consumer is present
			Optional<Consumer> cOpt = consumerRepository.findById(consumerId);
			if(cOpt.isPresent()) { // TODO return error otherwise
				Consumer consumer = cOpt.get();
				consumer.getConsumerRequests().add(consumerRequest);
				consumerRepository.save(consumer);
			}
			
			return createdConsumerRequest;
		}

		return consumerRequest;
	}
	
	@PostMapping("/client/{id}/bookAppointment")
	public Appointment appointmentAdd(@PathVariable Long id, @RequestBody Appointment appointment){
		Optional<Client> clientOpt = repository.findById(id);
		if(clientOpt.isPresent()) {		// TODO report error otherwise
			Client client = clientOpt.get();
			//  check if consumer is present
			Optional<Consumer> cOpt = consumerRepository.findById(appointment.getConsumerId());
			if(cOpt.isPresent()) {		// TODO report error otherwise
				Consumer consumer = cOpt.get();
				// check if provider is present
				Optional<ServiceProvider> providerOpt = serviceProviderRepository.findById(appointment.getProviderID());
				if(providerOpt.isPresent()) { 		// TODO report error otherwise
					ServiceProvider provider = providerOpt.get();
					Appointment createdAppointment = appointmentRepository.save(appointment);
					
					consumer.getAppointments().add(appointment);
					consumerRepository.save(consumer);
					
					provider.getBookings().add(appointment);
					serviceProviderRepository.save(provider);
					
					return createdAppointment;
				}
			}
		}

		return appointment;
	}

	@DeleteMapping("/client/{id}/consumer/{consumerId}")
	void deleteCustomer(@PathVariable Long id, @PathVariable Long consumerId) {
		consumerRepository.deleteById(consumerId);
	}

	@DeleteMapping("/client/{id}/service_providers/{serviceProviderId}")
	void deleteServiceProviders(@PathVariable Long id, @PathVariable Long serviceProviderId) {
		serviceProviderRepository.deleteById(serviceProviderId);
	}

	@DeleteMapping("/client/{id}/appointment/{appointmentId}")
	void deleteAppointment(@PathVariable Long id, @PathVariable Long appointmentId) {
		appointmentRepository.deleteById(appointmentId);

		//TODO confirm if we also need to delete from serviceProviders and consumers appointment lists and figure out.
	}

	@DeleteMapping("/client/{id}/consumerRequest/{consumerRequestId}")
	void deleteConsumerRequest(@PathVariable Long id, @PathVariable Long consumerRequestId) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) {
			client = clientOpt.get();
			consumerRequestRepository.deleteById(consumerRequestId);
		}
	}
}
