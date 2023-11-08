package com.tabsnotspaces.match;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long parentClientId; // TODO define as a joint key?
    private String providerName;
    private String address;

    @OneToMany
    private List<Service> services;
    @ManyToMany
    private List<Review> reviews;

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
     * @param availabilities   The availabilities of the service provider.
     * @param avgRating        The average rating of the service provider.
     * @param bookings         The list of appointments booked with the service provider.
     */
    public ServiceProvider(long parentClientId, String providerName, String address, List<Double> location, List<TupleDateTime> availabilities, long avgRating, List<Appointment> bookings) {
        this.parentClientId = parentClientId;
        this.providerName = providerName;
        this.address = address;
        this.location = location;
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


    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
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

    public List<Review> getReviews() { return reviews; }
}
