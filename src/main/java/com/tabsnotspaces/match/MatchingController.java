package com.tabsnotspaces.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@Validated
@CrossOrigin(origins = "http://localhost:4200/")
public class MatchingController {

	@Autowired
	ClientRepository repository;

	@Autowired
	ConsumerRepository consumerRepository;

	@Autowired
	ServiceProviderRepository serviceProviderRepository;

	@Autowired
	ServiceRepository serviceRepository;

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

		return new ResponseEntity<Client> (client, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/clientByName/{name}")
	public ResponseEntity<Object> clientByName(@PathVariable @NotNull(message = "client name should not be null") String name) {
		Optional<Client> existingClient = repository.findByClientNameIgnoreCase(name);
		if (existingClient.isEmpty()) {
            return ResponseEntity.badRequest().body(String.format("Client with name: '%s' does not exist!", name));
        }

		return ResponseEntity.ok(existingClient.get());
	}

	/**
	 * Retrieve a list of all clients.
	 *
	 * @return An iterable list of all clients.
	 */
	@GetMapping("/clients")
	public Iterable<Client> clientsList(){
		return repository.findAll();
	}

	/**
	 * Create a new client.
	 *
	 * @param client The new client to add.
	 * @return The newly created client.
	 */
	@PostMapping("/clients")
	public ResponseEntity<Object> clientsAdd(@Valid @RequestBody Client client) {
		Optional<Client> existingClient = repository.findByClientNameIgnoreCase(client.getClientName());
		if (existingClient.isPresent()) {
			return ResponseEntity.badRequest().body(String.format("Client with name: '%s' already exist!", client.getClientName()));
		}

		return ResponseEntity.ok(repository.save(client));
	}

	@GetMapping("/client/{id}/review")
	public List<Review> getReview(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id) {
		return (List<Review>) reviewRepository.findAll();
	}

	/**
	 * Delete a client by ID.
	 *
	 * @param id The ID of the client to delete.
	 */
	@DeleteMapping("/client/{id}")
	void deleteClient(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id) {
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
	public ResponseEntity<Object> consumerAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id,
									  @Valid @RequestBody Consumer consumer) {
		Optional<Client> clientOpt = repository.findById(id);
		if (clientOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Client with ID: '%d' does not exist!", id));
		}

		Client client = clientOpt.get();

		Optional<Consumer> existingConsumer = consumerRepository.findByParentClientIdAndConsumerNameIgnoreCase(id, consumer.getConsumerName());
		if (existingConsumer.isPresent()) {
			return ResponseEntity.badRequest().body(String.format("Consumer with name: '%s' already exist in client: '%s'!", consumer.getConsumerName(), client.getClientName()));
		}

		Consumer createdConsumer = consumerRepository.save(consumer);
		client.getConsumers().add(consumer);
		repository.save(client);

		return ResponseEntity.ok(createdConsumer);
	}

	@GetMapping("/client/{id}/consumer/{name}")
	public ResponseEntity<Object> consumerGet(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id,
									  @PathVariable @NotNull(message = "consumer name should not be null") String name) {
		Optional<Client> clientOpt = repository.findById(id);
		if (clientOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Client with ID: '%d' does not exist!", id));
		}

		Client client = clientOpt.get();

		Optional<Consumer> existingConsumer = consumerRepository.findByParentClientIdAndConsumerNameIgnoreCase(id, name);
		if (existingConsumer.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Consumer with name: '%s' does not exist in client: '%s'!", name, client.getClientName()));
		}

		return ResponseEntity.ok(existingConsumer.get());
	}

	/**
	 * Add a new service provider to a client.
	 *
	 * @param id               The ID of the client.
	 * @param serviceProvider   The new service provider to add.
	 * @return The newly created service provider.
	 */
	@PostMapping("/client/{id}/serviceProvider")
	public ResponseEntity<Object> serviceProviderAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @Valid @RequestBody ServiceProvider serviceProvider) {
		Optional<Client> clientOpt = repository.findById(id);
		if (clientOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Client with ID: '%d' does not exist!", id));
		}

		Client client = clientOpt.get();
		Optional<ServiceProvider> existingServiceProvider = serviceProviderRepository.findByParentClientIdAndProviderNameIgnoreCase(id, serviceProvider.getProviderName());
		if (existingServiceProvider.isPresent()) {
			return ResponseEntity.badRequest().body(String.format("Service Provider with name: '%s' already exist in client: '%s'!", serviceProvider.getProviderName(), client.getClientName()));
		}

		serviceProvider.setParentClientId(id);
		ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
		client.getServiceProviders().add(serviceProvider);
		repository.save(client);

		return ResponseEntity.ok(createdServiceProvider);
	}

	/**
	 * Add a new service provider to a client.
	 *
	 * @param id      The ID of the client.
	 * @param service The new service provider to add.
	 * @return The newly created service provider.
	 */
	@PostMapping("/client/{id}/service")
	public ResponseEntity<Object> serviceAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @Valid @RequestBody Service service) {
		Optional<Client> clientOpt = repository.findById(id);

		if (clientOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Client with ID: '%d' does not exist!", id));
		}

		Optional<ServiceProvider> serviceProviderOpt = serviceProviderRepository.findById(service.getProviderId());

		if (serviceProviderOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Service Provider with ID: '%d' does not exist!", service.getProviderId()));
		}

		Client client = clientOpt.get();

		Optional<Service> existingService = serviceRepository.findByProviderIdAndServiceNameIgnoreCase(service.getProviderId(), service.getServiceName());
		if (existingService.isPresent()) {
			return ResponseEntity.badRequest().body(String.format("Service with name: '%s' already exist in client: '%s'!", service.getServiceName(), serviceProviderOpt.get().getProviderName()));
		}

		Service createdService = serviceRepository.save(service);
		ServiceProvider serviceProvider = client.getServiceProvider(service.getProviderId());
		if (serviceProvider != null) {
			serviceProvider.getServices().add(service);
		}
		repository.save(client);

		return ResponseEntity.ok(createdService);
	}

	@PostMapping("/client/{id}/addReview")
	public ResponseEntity<Object> reviewAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @Valid @RequestBody Review review) {
		Optional<Client> clientOpt = repository.findById(id);
		if (clientOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Client with ID: '%d' does not exist!", id));
		}

		Optional<ServiceProvider> serviceProviderOpt = serviceProviderRepository.findById(review.getServiceProviderId());
		if (serviceProviderOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Service Provider with ID: '%d' does not exist!", review.getServiceProviderId()));
		}

		Optional<Consumer> consumerOpt = consumerRepository.findById(review.getConsumerId());
		if (consumerOpt.isEmpty()) {
			return ResponseEntity.badRequest().body(String.format("Consumer with ID: '%d' does not exist!", review.getConsumerId()));
		}

		Client client = clientOpt.get();
		ServiceProvider serviceProvider = serviceProviderOpt.get();
		serviceProvider.getReviews().add(review);
		updateAverage(serviceProvider);

		Review createdReview = reviewRepository.save(review);
		client.getReviews().add(review);
		repository.save(client);

		return ResponseEntity.ok(createdReview);
	}


	@GetMapping("/client/{id}/getAvailability")
	public ResponseEntity<List<TupleDateTime>> getAvailability(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @RequestParam @Min(value = 1, message = "provider id should be greater than 0") Long providerId) {
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
	 * Retrieve a list of all service providers associated with the client ID.
	 *
	 * @param consumerRequest The consumer request with date and service type.
	 * @return A list of service providers matching the request, sorted by proximity and rating.
	 */
	@GetMapping("/client/{id}/allProviders")
	public ResponseEntity<List<ServiceProvider>> getAllProviders(@PathVariable Long id) {
		Optional<Client> cOpt = repository.findById(id);
		if(cOpt.isPresent()) {
			List<ServiceProvider> serviceProviders = serviceProviderRepository.findByParentClientId(id);
			return new ResponseEntity<List<ServiceProvider>> (serviceProviders, HttpStatus.OK);
		}
		else {
			List<ServiceProvider> nullList = null;
			return new ResponseEntity<List<ServiceProvider>> (nullList, HttpStatus.BAD_REQUEST);
		}
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
		Service requestedService = consumerRequest.getServiceType();
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
			if (!(availableProviders.get(i).getServices().contains(requestedService))) {
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
					//if (!provider.getServices().contains(appointment.getServiceType())) {
					//	throw new RuntimeException("Service not available for this provider");
					//}
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
	 * Delete an appointment by ID.
	 *
	 * @param id              The ID of the client.
	 * @param appointmentId   The ID of the appointment to delete.
	 */
	@DeleteMapping("/client/{id}/review/{reviewId}")
	void deleteReview(@PathVariable Long id, @PathVariable Long reviewId) {
		reviewRepository.deleteById(reviewId);
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