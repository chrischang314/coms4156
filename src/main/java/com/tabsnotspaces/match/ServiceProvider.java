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
    private String location;
    private List<String> servicesOffered; // TODO convert to an entity type serviceNum?
    @ElementCollection
    @CollectionTable(name = "service_provider_availabilities", joinColumns = @JoinColumn(name = "service_provider_id"))
    private List<TupleDateTime> availabilities;
    private long avgRating;
    @ManyToMany
    private List<Appointment> bookings;
    public ServiceProvider(long parentClientId, String providerName, String location, List<String> servicesOffered, List<TupleDateTime> availabilities, long avgRating, List<Appointment> bookings) {
        this.parentClientId = parentClientId;
        this.providerName = providerName;
        this.location = location;
        this.servicesOffered = servicesOffered;
        this.availabilities = availabilities;
        this.avgRating = avgRating;
        this.bookings = bookings;
    }

    public ServiceProvider() {
        super();
    }

    public ServiceProvider(long parentClientId) {
        super();
        this.parentClientId = parentClientId;
    }

    public long getId() {
        return id;
    }
    // TODO make list of ratings for each servicesOffered

    public void setId(long id) {
        this.id = id;
    }

    public List<TupleDateTime> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<TupleDateTime> availabilities) {
        this.availabilities = availabilities;
    }

    public long getParentClientId() {
        return parentClientId;
    }

    public void setParentClientId(long parentClientId) {
        this.parentClientId = parentClientId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String name) {
        providerName = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(List<String> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    /*
     * public List<LocalDateTime> getAvailability() { return availability; }
     *
     * public void setAvailability(List<LocalDateTime> availability) {
     * this.availability = availability; }
     */
    public long getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(long avgRating) {
        this.avgRating = avgRating;
    }

    public List<Appointment> getBookings() {
        return bookings;
    }

    public void setBookings(List<Appointment> bookings) {
        this.bookings = bookings;
    }
}
