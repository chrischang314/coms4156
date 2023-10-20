package com.tabsnotspaces.match;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long parentClientId; // TODO define as a joint key?
    private String providerName;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "provider_service",
            joinColumns = @JoinColumn(name = "provider_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services;

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

    private List<Double> location;
    @ElementCollection
    @CollectionTable(name = "service_provider_availabilities", joinColumns = @JoinColumn(name = "service_provider_id"))
    private List<TupleDateTime> availabilities;
    private long avgRating;
    @ManyToMany
    private List<Appointment> bookings;

    /**
     * Constructs a ServiceProvider with the provided attributes.
     *
     * @param parentClientId The ID of the parent client.
     * @param providerName   The name of the service provider.
     * @param address        The address of the service provider.
     * @param location       The location coordinates of the service provider.
     * @param servicesOffered The list of services offered by the provider.
     * @param availabilities   The availabilities of the service provider.
     * @param avgRating        The average rating of the service provider.
     * @param bookings         The list of appointments booked with the service provider.
     */
    public ServiceProvider(long parentClientId, String providerName, String address, List<Double> location, Set<Service> servicesOffered, List<TupleDateTime> availabilities, long avgRating, List<Appointment> bookings) {
        this.parentClientId = parentClientId;
        this.providerName = providerName;
        this.address = address;
        this.location = location;
        this.services = servicesOffered;
        this.availabilities = availabilities;
        this.avgRating = avgRating;
        this.bookings = bookings;
    }

    /**
     * Default constructor for ServiceProvider.
     */
    public ServiceProvider() {
        super();
    }

    /**
     * Constructs a ServiceProvider with the provided parent client ID.
     *
     * @param parentClientId The ID of the parent client.
     */
    public ServiceProvider(long parentClientId) {
        super();
        this.parentClientId = parentClientId;
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
     * Get the parent client ID of the service provider.
     *
     * @return The parent client ID of the service provider.
     */
    public long getParentClientId() {
        return parentClientId;
    }

    /**
     * Set the parent client ID of the service provider.
     *
     * @param parentClientId The parent client ID of the service provider.
     */
    public void setParentClientId(long parentClientId) {
        this.parentClientId = parentClientId;
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
     * Get the list of services offered by the provider.
     *
     * @return The list of services offered by the provider.
     */
    public Set<Service> getServicesOffered() {
        return services;
    }

    /**
     * Set the list of services offered by the provider.
     *
     * @param servicesOffered The list of services offered by the provider.
     */
    public void setServicesOffered(Set<Service> servicesOffered) {
        this.services = servicesOffered;
    }

    /*
     * public List<LocalDateTime> getAvailability() { return availability; }
     *
     * public void setAvailability(List<LocalDateTime> availability) {
     * this.availability = availability; }
     */
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
}
