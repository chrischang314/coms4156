package com.tabsnotspaces.match;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;


@Entity
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "provider_service",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;

    @ManyToMany
    @JoinTable(
            name = "client_provider",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Client> clients;

    private String providerName;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public List<Double> getLocation() {
        return location;
    }

    public ServiceProvider(Set<Service> services, Set<Client> clients, String providerName, String address, List<Double> location, List<TupleDateTime> availabilities, long avgRating, List<Appointment> bookings) {
        this.services = services;
        this.clients = clients;
        this.providerName = providerName;
        this.address = address;
        this.location = location;
        this.availabilities = availabilities;
        this.avgRating = avgRating;
        this.bookings = bookings;
    }

    private List<Double> location;
    @ElementCollection
    @CollectionTable(name = "service_provider_availabilities", joinColumns = @JoinColumn(name = "service_provider_id"))
    private List<TupleDateTime> availabilities;
    private long avgRating;
    @ManyToMany
    private List<Appointment> bookings;

    /**
     * Default constructor for ServiceProvider.
     */
    public ServiceProvider() {
        super();
    }


    /**
     * Get the ID of the service provider.
     *
     * @return The ID of the service provider.
     */
    public long getId() {
        return id;
    }
    // TODO make list of ratings for each servicesOffered

    /**
     * Set the ID of the service provider.
     *
     * @param id The ID of the service provider.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the availabilities of the service provider.
     *
     * @return The availabilities of the service provider.
     */
    public List<TupleDateTime> getAvailabilities() {
        return availabilities;
    }

    /**
     * Set the availabilities of the service provider.
     *
     * @param availabilities The availabilities of the service provider.
     */
    public void setAvailabilities(List<TupleDateTime> availabilities) {
        this.availabilities = availabilities;
    }

    /**
     * Get the name of the service provider.
     *
     * @return The name of the service provider.
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Set the name of the service provider.
     *
     * @param name The name of the service provider.
     */
    public void setProviderName(String name) {
        providerName = name;
    }

    /**
     * Get the average rating of the service provider.
     *
     * @return The average rating of the service provider.
     */
    public long getAvgRating() {
        return avgRating;
    }

    /**
     * Set the average rating of the service provider.
     *
     * @param avgRating The average rating of the service provider.
     */
    public void setAvgRating(long avgRating) {
        this.avgRating = avgRating;
    }

    /**
     * Get the list of appointments booked with the service provider.
     *
     * @return The list of appointments booked with the service provider.
     */
    public List<Appointment> getBookings() {
        return bookings;
    }

    /**
     * Set the list of appointments booked with the service provider.
     *
     * @param bookings The list of appointments booked with the service provider.
     */
    public void setBookings(List<Appointment> bookings) {
        this.bookings = bookings;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}