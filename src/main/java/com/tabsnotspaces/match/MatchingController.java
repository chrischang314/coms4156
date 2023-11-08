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


@RestController
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
     * @param name  The name of the new client.
     * @param model The model for the client.
     * @return The newly created client.
     */
    @PostMapping("/clients")
    public Client clientsAdd(@RequestParam String name, Model model) {
        Client client = new Client();
        client.setClientName(name);

        return repository.save(client);
    }

    @GetMapping("/client{id}/review")
    public List<Review> getReview(@PathVariable Long id) {
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
    public Consumer consumerAdd(@PathVariable Long id,
                                @RequestBody Consumer consumer) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) { // TODO report error otherwise
            client = clientOpt.get();
            // TODO check if consumer is present

            Consumer createdConsumer = consumerRepository.save(consumer);
            client.getConsumers().add(consumer);
            repository.save(client);

            return createdConsumer;
        }

        return consumer;
    }

    /**
     * Add a new service provider to a client.
     *
     * @param id              The ID of the client.
     * @param serviceProvider The new service provider to add.
     * @return The newly created service provider.
     */
    @PostMapping("/client/{id}/serviceProvider")
    public ServiceProvider serviceProviderAdd(@PathVariable Long id, @RequestBody ServiceProvider serviceProvider) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) { // TODO report error otherwise
            client = clientOpt.get();
            // TODO check if service provider is present
            serviceProvider.setParentClientId(id);
            ServiceProvider createdServiceProvider = serviceProviderRepository.save(serviceProvider);
            client.getServiceProviders().add(serviceProvider);
            repository.save(client);

            return createdServiceProvider;
        }

        return serviceProvider;
    }

    /**
     * Add a new service provider to a client.
     *
     * @param id      The ID of the client.
     * @param service The new service provider to add.
     * @return The newly created service provider.
     */
    @PostMapping("/client/{id}/service")
    public Service serviceAdd(@PathVariable Long id, @RequestBody Service service) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) { // TODO report error otherwise
            client = clientOpt.get();
            // TODO check if service provider is present
            Service createdService = serviceRepository.save(service);
            ServiceProvider serviceProvider = client.getServiceProvider(service.getProviderId());
            if (serviceProvider != null) {
                serviceProvider.getServices().add(service);
            }
            repository.save(client);

            return createdService;
        }

        return service;
    }

    @PostMapping("/client/{id}/addAvailability")
    public List<TupleDateTime> addAvailability(@PathVariable Long id, @RequestParam Long providerId,
                                               @RequestParam List<TupleDateTime> addOnAvailabilities) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) {
            client = clientOpt.get();
            Optional<ServiceProvider> sP = serviceProviderRepository.findById(providerId);
            if (sP.isPresent()) {
                ServiceProvider serviceProvider = sP.get();
                List<TupleDateTime> currentAvailabilities = serviceProvider.getAvailabilities();
                currentAvailabilities.addAll(addOnAvailabilities);
                serviceProviderRepository.save(serviceProvider); // is this necessary?
                return currentAvailabilities;
            }
        }
        return addOnAvailabilities;
    }

    @DeleteMapping("/client/{id}/deleteAvailability")
    public void deleteAvailability(@PathVariable Long id, @RequestParam Long providerId,
                                   @RequestParam List<TupleDateTime> removeAvailabilities) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) {
            client = clientOpt.get();
            Optional<ServiceProvider> sP = serviceProviderRepository.findById(providerId);
            if (sP.isPresent()) {
                ServiceProvider serviceProvider = sP.get();
                List<TupleDateTime> currentAvailabilities = serviceProvider.getAvailabilities();
                for (TupleDateTime removeAvailability : removeAvailabilities) {
                    currentAvailabilities.remove(removeAvailability);
                }
                serviceProviderRepository.save(serviceProvider);
            }
        }
    }

    @PostMapping("/client/{id}/addReview")
    public Review reviewAdd(@PathVariable Long id, @RequestBody Review review) {
        Optional<Client> clientOpt = repository.findById(id);
        Client client = null;
        if (clientOpt.isPresent()) { // TODO report error otherwise
            client = clientOpt.get();
            // TODO check if service provider is present
            Review createdReview = reviewRepository.save(review);
            ServiceProvider serviceProvider = client.getServiceProvider(review.getServiceProviderId());
            if (serviceProvider != null) {
                serviceProvider.getReviews().add(review);
            }
            repository.save(client);

            return createdReview;
        }

        return review;
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
                    if (!provider.getServices().contains(appointment.getService())) {
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
