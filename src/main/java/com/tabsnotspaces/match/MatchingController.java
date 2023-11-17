package com.tabsnotspaces.match;

import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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

	@Autowired
	ReviewRepository reviewRepository;

	/**
	 * Retrieve a client by ID.
	 *
	 * @param id The ID of the client to retrieve.
	 * @return The client with the specified ID.
	 */
	@GetMapping("/client/{id}")
	public ResponseEntity<Client> client(@PathVariable Long id) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) {
			client = clientOpt.get();
			return new ResponseEntity<Client> (client, HttpStatus.ACCEPTED);
		}
		// https://stackoverflow.com/questions/50904742/property-or-field-name-cannot-be-found-on-object-of-type-java-util-optional
		return new ResponseEntity<Client> (client, HttpStatus.BAD_REQUEST);
	}

	
	// https://stackoverflow.com/questions/57184276/the-method-findonelong-is-undefined-for-the-type-personrepository

	/**
	 * Retrieve a list of all clients.
	 *
	 * @return An iterable list of all clients.
	 */
	@GetMapping("/clients")
	public Iterable<Client> clientsList(){
		return repository.findAll();
	}
	
	// TODO change to @RequestBody Client client as input
	/**
	 * Create a new client.
	 *
	 * @param name  The name of the new client.
	 * @param model The model for the client.
	 * @return The newly created client.
	 */
	@PostMapping("/clients")
	public ResponseEntity<Client> clientsAdd(@RequestParam String name, Model model){
		Client client = new Client();
		client.setClientName(name);
		
		return new ResponseEntity<Client>(repository.save(client), HttpStatus.CREATED);
	}

	@GetMapping("/client{id}/review")
	public List<Review> getReview (@PathVariable Long id) {
		return (List<Review>) reviewRepository.findAll();
	}

	/**
	 * Delete a client by ID.
	 *
	 * @param id The ID of the client to delete.
	 */
	@DeleteMapping("/client/{id}")
	void deleteClient(@PathVariable Long id) {
		repository.deleteById(id);
	}

	/**
	 * Add a new consumer to a client.
	 *
	 * @param id       The ID of the client.
	 * @param consumer The new consumer to add.
	 * @return The newly created consumer.
	 */
	@PostMapping("/client/{id}/consumer")
	public ResponseEntity<Consumer> consumerAdd(@PathVariable Long id,
			@RequestBody Consumer consumer){
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) { // TODO report error otherwise
			client = clientOpt.get();
			// TODO check if consumer is present
			
			Consumer createdConsumer = consumerRepository.save(consumer);
			client.getConsumers().add(consumer);
			repository.save(client);
			
			return new ResponseEntity<Consumer>(createdConsumer, HttpStatus.CREATED);
		}

		return new ResponseEntity<Consumer>(consumer, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Add a new service provider to a client.
	 *
	 * @param id               The ID of the client.
	 * @param serviceProvider   The new service provider to add.
	 * @return The newly created service provider.
	 */
	@PostMapping("/client/{id}/serviceProvider")
	public ResponseEntity<ServiceProvider> serviceProviderAdd(@PathVariable Long id, @RequestBody ServiceProvider serviceProvider){
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) { // TODO report error otherwise
			client = clientOpt.get();
			// TODO check if service provider is present			
			ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
			client.getServiceProviders().add(serviceProvider);
			repository.save(client);

			return new ResponseEntity<ServiceProvider>(createdServiceProvider, HttpStatus.CREATED);
		}

		return new ResponseEntity<ServiceProvider>(serviceProvider, HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/client/{id}/addReview")
	public ResponseEntity<Review> reviewAdd(@PathVariable Long id, @RequestBody Review review) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		ServiceProvider serviceProvider = null;
		if(clientOpt.isPresent()) { // TODO report error otherwise
			client = clientOpt.get();
			Optional<ServiceProvider> providerOpt = serviceProviderRepository.findById(review.getServiceProviderId());
			if (providerOpt.isPresent()) {
				serviceProvider = providerOpt.get();
				serviceProvider.getReviews().add(review);
				updateAverage(serviceProvider);
			}

			Review createdReview = reviewRepository.save(review);
			client.getReviews().add(review);
			repository.save(client);

			return new ResponseEntity<Review> (createdReview, HttpStatus.CREATED);
		}

		return new ResponseEntity<Review> (review, HttpStatus.BAD_REQUEST);
	}


	@GetMapping("/client/{id}/getAvailability")
	public ResponseEntity<List<TupleDateTime>> getAvailability(@PathVariable Long id, @RequestParam Long providerId) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if (clientOpt.isPresent()) {
			client = clientOpt.get();
			Optional<ServiceProvider> sP = serviceProviderRepository.findById(providerId);
			if (sP.isPresent()) {
				ServiceProvider serviceProvider = sP.get();
				List<TupleDateTime> currentAvailabilities = serviceProvider.getAvailabilities();
				return new ResponseEntity<List<TupleDateTime>> (currentAvailabilities, HttpStatus.ACCEPTED);
			}
		}
		
		List<TupleDateTime> nullList = null;
		return new ResponseEntity<List<TupleDateTime>> (nullList, HttpStatus.BAD_REQUEST);
    }
	
	@PostMapping("/client/{id}/addAvailability")
	public ResponseEntity<TupleDateTime> addAvailability(@PathVariable Long id, @RequestParam Long providerId,
										   @RequestBody TupleDateTime newAvailability) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if (clientOpt.isPresent()) {
			client = clientOpt.get();
			Optional<ServiceProvider> sP = serviceProviderRepository.findById(providerId);
			if (sP.isPresent()) {
				ServiceProvider serviceProvider = sP.get();
				List<TupleDateTime> currentAvailabilities = serviceProvider.getAvailabilities();
				currentAvailabilities.add(newAvailability);
				serviceProviderRepository.save(serviceProvider); // is this necessary?
				
				return new ResponseEntity<TupleDateTime> (newAvailability, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<TupleDateTime> (newAvailability, HttpStatus.BAD_REQUEST);
    }

	@DeleteMapping("/client/{id}/deleteAvailability")
	public ResponseEntity deleteAvailability(@PathVariable Long id, @RequestParam Long providerId,
			@RequestBody TupleDateTime expiredAvailability) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if (clientOpt.isPresent()) {
			client = clientOpt.get();
			Optional<ServiceProvider> sP = serviceProviderRepository.findById(providerId);
			if (sP.isPresent()) {
				ServiceProvider serviceProvider = sP.get();
				List<TupleDateTime> currentAvailabilities = serviceProvider.getAvailabilities();
				TupleDateTime removeAvailability = null;
				for (TupleDateTime currentAvailability : currentAvailabilities) {
					if(currentAvailability.getStartTime().equals(expiredAvailability.getStartTime())
							&& currentAvailability.getEndTime().equals(expiredAvailability.getEndTime()))
						removeAvailability = currentAvailability;
				}
				currentAvailabilities.remove(removeAvailability);
				serviceProviderRepository.save(serviceProvider);
				
				return new ResponseEntity(HttpStatus.ACCEPTED);
			}
		}
		
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	//@PostMapping("/client/{id}/consumerRequest")
	/**
	 * Add a new consumer request to a client.
	 *
	 * @param id                The ID of the client.
	 * @param consumerRequest   The new consumer request to add.
	 * @return The newly created consumer request.
	 */
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

	/**
	 * Retrieve a list of service providers sorted by proximity and rating.
	 *
	 * @param consumerRequest The consumer request with date and service type.
	 * @return A list of service providers matching the request, sorted by proximity and rating.
	 */
	@PostMapping("/client/{id}/consumerRequest")
	public ResponseEntity<List<ServiceProvider>> sortedProvidersResponse(@RequestBody ConsumerRequest consumerRequest) {
		TupleDateTime requestedDate = consumerRequest.getRequestDate();
		String requestedService = consumerRequest.getServiceType();
		Consumer consumer = new Consumer();
		Optional<Consumer> cOpt = consumerRepository.findById(consumerRequest.getConsumerId());
		if(cOpt.isPresent()) {
			consumer = cOpt.get();
		}
		else {
			List<ServiceProvider> nullList = null;
			return new ResponseEntity<List<ServiceProvider>> (nullList, HttpStatus.BAD_REQUEST);
		}
		// Fetch providers who match the requested date & service
		List<ServiceProvider> availableProviders =
				new ArrayList<ServiceProvider>(serviceProviderRepository.findByAvailabilities(requestedDate));
		for (int i = 0; i < availableProviders.size(); i++) {
			if (!(availableProviders.get(i).getServicesOffered().contains(requestedService))) {
				availableProviders.remove(availableProviders.get(i));
			}
		}
		Collections.sort(availableProviders, new ServiceProviderComparator(consumer));
		return new ResponseEntity<List<ServiceProvider>> (availableProviders, HttpStatus.ACCEPTED);
	}

	/**
	 * Book an appointment with a service provider.
	 *
	 * @param id          The ID of the client.
	 * @param appointment The new appointment to book.
	 * @return The newly created appointment.
	 */
	@PostMapping("/client/{id}/bookAppointment")
	public ResponseEntity<Appointment> appointmentAdd(@PathVariable Long id, @RequestBody Appointment appointment){
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

					// check that service provider is available for appointment and service requested matches
					if (!provider.getServicesOffered().contains(appointment.getServiceType())) {
						throw new RuntimeException("Service not available for this provider");
					}
					if (!provider.getAvailabilities().contains(appointment.getAppointmentTime())) {
						throw new RuntimeException("Service provider not available at the requested time");
					}

					Appointment createdAppointment = appointmentRepository.save(appointment);
					
					consumer.getAppointments().add(appointment);
					consumerRepository.save(consumer);
					
					provider.getBookings().add(appointment);
					serviceProviderRepository.save(provider);
					
					return new ResponseEntity<Appointment>(createdAppointment, HttpStatus.CREATED);
				}
			}
		}

		return new ResponseEntity<Appointment>(appointment, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Delete a consumer by ID.
	 *
	 * @param id         The ID of the client.
	 * @param consumerId The ID of the consumer to delete.
	 */
	@DeleteMapping("/client/{id}/consumer/{consumerId}")
	void deleteCustomer(@PathVariable Long id, @PathVariable Long consumerId) {
		consumerRepository.deleteById(consumerId);
	}

	/**
	 * Delete a service provider by ID.
	 *
	 * @param id                  The ID of the client.
	 * @param serviceProviderId    The ID of the service provider to delete.
	 */
	@DeleteMapping("/client/{id}/service_providers/{serviceProviderId}")
	void deleteServiceProviders(@PathVariable Long id, @PathVariable Long serviceProviderId) {
		serviceProviderRepository.deleteById(serviceProviderId);
	}

	/**
	 * Delete an appointment by ID.
	 *
	 * @param id              The ID of the client.
	 * @param appointmentId   The ID of the appointment to delete.
	 */
	@DeleteMapping("/client/{id}/appointment/{appointmentId}")
	void deleteAppointment(@PathVariable Long id, @PathVariable Long appointmentId) {
		appointmentRepository.deleteById(appointmentId);

		//TODO confirm if we also need to delete from serviceProviders and consumers appointment lists and figure out.
	}

	/**
	 * Delete a consumer request by ID.
	 *
	 * @param id                   The ID of the client.
	 * @param consumerRequestId    The ID of the consumer request to delete.
	 */
	@DeleteMapping("/client/{id}/consumerRequest/{consumerRequestId}")
	ResponseEntity deleteConsumerRequest(@PathVariable Long id, @PathVariable Long consumerRequestId) {
		Optional<Client> clientOpt = repository.findById(id);
		Client client = null;
		if(clientOpt.isPresent()) {
			client = clientOpt.get();
			consumerRequestRepository.deleteById(consumerRequestId);
			// TODO check if delete was successful and return code, for all delete above too
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Update a service provider's average rating.
	 *
	 * @param serviceProvider       The service provider to update.
	 */
	void updateAverage(ServiceProvider serviceProvider) {
		int sum = 0;
		List<Review> reviews = serviceProvider.getReviews();
		for (Review review : reviews) {
			sum += review.getRating();
		}
		long average = (sum/reviews.size());
		serviceProvider.setAvgRating(average);
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
