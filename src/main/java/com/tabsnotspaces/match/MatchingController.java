package com.tabsnotspaces.match;

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
@CrossOrigin(origins = "http://localhost:4200")
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
    public Client client(@PathVariable Long id) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) {
            client = clientOpt.get();
        }
        // https://stackoverflow.com/questions/50904742/property-or-field-name-cannot-be-found-on-object-of-type-java-util-optional
        return client;
    }

    @GetMapping("/clientByName/{name}")
    public ResponseEntity clientByName(@PathVariable @NotNull(message = "client name should not be null") String name) {
        Optional<Client> existingClient = repository.findByClientNameIgnoreCase(name);
        if (existingClient.isEmpty()) {
            return ResponseEntity.badRequest().body(String.format("Client with name: '%s' does not exist!", name));
        }

        return ResponseEntity.ok(existingClient.get());
    }

    // https://stackoverflow.com/questions/57184276/the-method-findonelong-is-undefined-for-the-type-personrepository

    /**
     * Retrieve a list of all clients.
     *
     * @return An iterable list of all clients.
     */
    @GetMapping("/clients")
    public Iterable<Client> clientsList() {
        return repository.findAll();
    }

    // TODO change to @RequestBody Client client as input

    /**
     * Create a new client.
     *
     * @param client The new client to add.
     * @return The newly created client.
     */
    @PostMapping("/clients")
    public ResponseEntity clientsAdd(@Valid @RequestBody Client client) {
        Optional<Client> existingClient = repository.findByClientNameIgnoreCase(client.getClientName());
        if (existingClient.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("Client with name: '%s' already exist!", client.getClientName()));
        }

        return ResponseEntity.ok(repository.save(client));
    }

    @GetMapping("/client{id}/review")
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
    public ResponseEntity consumerAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id,
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

    /**
     * Add a new service provider to a client.
     *
     * @param id              The ID of the client.
     * @param serviceProvider The new service provider to add.
     * @return The newly created service provider.
     */
    @PostMapping("/client/{id}/serviceProvider")
    public ResponseEntity serviceProviderAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @Valid @RequestBody ServiceProvider serviceProvider) {
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

    @GetMapping("/client/{id}/consumer/{name}")
    public ResponseEntity consumerGet(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id,
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
     * @param id      The ID of the client.
     * @param service The new service provider to add.
     * @return The newly created service provider.
     */
    @PostMapping("/client/{id}/service")
    public ResponseEntity serviceAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @Valid @RequestBody Service service) {
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
            return ResponseEntity.badRequest().body(String.format("Service with name: '%s' already exist in service provider: '%s'!", service.getServiceName(), serviceProviderOpt.get().getProviderName()));
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
    public ResponseEntity reviewAdd(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @Valid @RequestBody Review review) {
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
    public List<TupleDateTime> getAvailability(@PathVariable @Min(value = 1, message = "client id should be greater than 0") Long id, @RequestParam @Min(value = 1, message = "provider id should be greater than 0") Long providerId) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) {
            client = clientOpt.get();
            Optional<ServiceProvider> sP = serviceProviderRepository.findById(providerId);
            if (sP.isPresent()) {
                ServiceProvider serviceProvider = sP.get();
                List<TupleDateTime> currentAvailabilities = serviceProvider.getAvailabilities();
                return currentAvailabilities;
            }
        }
        return null;
    }

    @PostMapping("/client/{id}/addAvailability")
    public TupleDateTime addAvailability(@PathVariable Long id, @RequestParam Long providerId,
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
            }
        }
        return newAvailability;
    }

    @DeleteMapping("/client/{id}/deleteAvailability")
    public void deleteAvailability(@PathVariable Long id, @RequestParam Long providerId,
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
                    if (currentAvailability.getStartTime().equals(expiredAvailability.getStartTime())
                            && currentAvailability.getEndTime().equals(expiredAvailability.getEndTime()))
                        removeAvailability = currentAvailability;
                }
                currentAvailabilities.remove(removeAvailability);
                serviceProviderRepository.save(serviceProvider);
            }
        }
    }

    //@PostMapping("/client/{id}/consumerRequest")

    /**
     * Add a new consumer request to a client.
     *
     * @param id              The ID of the client.
     * @param consumerRequest The new consumer request to add.
     * @return The newly created consumer request.
     */
    public ConsumerRequest consumerAdd(@PathVariable Long id, @RequestBody ConsumerRequest consumerRequest) {
        Optional<Client> clientOpt = repository.findById(id);
        if (clientOpt.isPresent()) { // TODO report error otherwise
            //  TODO check if consumer request is present
            ConsumerRequest createdConsumerRequest = consumerRequestRepository.save(consumerRequest);

            long consumerId = consumerRequest.getConsumerId();
            //  check if consumer is present
            Optional<Consumer> cOpt = consumerRepository.findById(consumerId);
            if (cOpt.isPresent()) { // TODO return error otherwise
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
    public List<ServiceProvider> sortedProvidersResponse(@RequestBody ConsumerRequest consumerRequest) {
        TupleDateTime requestedDate = consumerRequest.getRequestDate();
        Service requestedService = consumerRequest.getServiceType();
        Consumer consumer = new Consumer();
        Optional<Consumer> cOpt = consumerRepository.findById(consumerRequest.getConsumerId());
        if (cOpt.isPresent()) { // TODO return error otherwise
            consumer = cOpt.get();
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
        return availableProviders;
    }

    /**
     * Book an appointment with a service provider.
     *
     * @param id          The ID of the client.
     * @param appointment The new appointment to book.
     * @return The newly created appointment.
     */
    @PostMapping("/client/{id}/bookAppointment")
    public Appointment appointmentAdd(@PathVariable Long id, @RequestBody Appointment appointment) {
        Optional<Client> clientOpt = repository.findById(id);
        if (clientOpt.isPresent()) {        // TODO report error otherwise
            Client client = clientOpt.get();
            //  check if consumer is present
            Optional<Consumer> cOpt = consumerRepository.findById(appointment.getConsumerId());
            if (cOpt.isPresent()) {        // TODO report error otherwise
                Consumer consumer = cOpt.get();
                // check if provider is present
                Optional<ServiceProvider> providerOpt = serviceProviderRepository.findById(appointment.getProviderID());
                if (providerOpt.isPresent()) {        // TODO report error otherwise
                    ServiceProvider provider = providerOpt.get();

                    // check that service provider is available for appointment and service requested matches
                    if (!provider.getServices().contains(appointment.getServiceType())) {
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

                    return createdAppointment;
                }
            }
        }

        return appointment;
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
     * @param id                The ID of the client.
     * @param serviceProviderId The ID of the service provider to delete.
     */
    @DeleteMapping("/client/{id}/service_providers/{serviceProviderId}")
    void deleteServiceProviders(@PathVariable Long id, @PathVariable Long serviceProviderId) {
        serviceProviderRepository.deleteById(serviceProviderId);
    }

    /**
     * Delete an appointment by ID.
     *
     * @param id            The ID of the client.
     * @param appointmentId The ID of the appointment to delete.
     */
    @DeleteMapping("/client/{id}/appointment/{appointmentId}")
    void deleteAppointment(@PathVariable Long id, @PathVariable Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);

        //TODO confirm if we also need to delete from serviceProviders and consumers appointment lists and figure out.
    }

    /**
     * Delete a consumer request by ID.
     *
     * @param id                The ID of the client.
     * @param consumerRequestId The ID of the consumer request to delete.
     */
    @DeleteMapping("/client/{id}/consumerRequest/{consumerRequestId}")
    void deleteConsumerRequest(@PathVariable Long id, @PathVariable Long consumerRequestId) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) {
            client = clientOpt.get();
            consumerRequestRepository.deleteById(consumerRequestId);
        }
    }

    /**
     * Update a service provider's average rating.
     *
     * @param serviceProvider The service provider to update.
     */
    void updateAverage(ServiceProvider serviceProvider) {
        int sum = 0;
        List<Review> reviews = serviceProvider.getReviews();
        for (Review review : reviews) {
            sum += review.getRating();
        }
        long average = (sum / reviews.size());
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
        } else {
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
