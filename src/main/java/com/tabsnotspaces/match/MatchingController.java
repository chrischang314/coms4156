package com.tabsnotspaces.match;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
	
	//@PostMapping("/client/{id}/consumerRequest")
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
	@PostMapping("/client/{id}/consumerRequest")
	public List<ServiceProvider> sortedProvidersResponse(@RequestBody ConsumerRequest consumerRequest) {
		Date requestedDate = consumerRequest.getRequestDate();
		String requestedService = consumerRequest.getServiceType();
		Consumer consumer = new Consumer();
		Optional<Consumer> cOpt = consumerRepository.findById(consumerRequest.getConsumerId());
		if(cOpt.isPresent()) { // TODO return error otherwise
			consumer = cOpt.get();
		}
		// Fetch providers who match the requested date & service
		List<ServiceProvider> availableProviders =
				new ArrayList<ServiceProvider>(serviceProviderRepository.findByAvailability(requestedDate));
		for (int i = 0; i < availableProviders.size(); i++) {
			if (!(availableProviders.get(i).getServicesOffered().contains(requestedService))) {
				availableProviders.remove(availableProviders.get(i));
			}
		}
		Collections.sort(availableProviders, new ServiceProviderComparator(consumer));
		return availableProviders;
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
	
}

class ServiceProviderComparator implements Comparator<ServiceProvider> {

	private Consumer c;

	ServiceProviderComparator(Consumer c) {
		this.c = c;
	}

	// Calculates distance between two lat/long coordinate pairs
	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double t = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
					Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(t));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			return (dist);
		}
	}

	// Sort service providers first by distance from consumer, then by rating
	@Override
	public int compare(ServiceProvider o1, ServiceProvider o2) {
		List<Double> consumerLocation = c.getLocation();
		double dist1 = distance(consumerLocation.get(0), consumerLocation.get(1), o1.getLocation().get(0), o1.getLocation().get(1));
		double dist2 = distance(consumerLocation.get(0), consumerLocation.get(1), o2.getLocation().get(0), o2.getLocation().get(1));
		int value1 = Double.compare(dist1, dist2);
		if (value1 == 0) {
			return Long.compare(o2.getAvgRating(), o1.getAvgRating());
		}
		return value1;
	}
}
