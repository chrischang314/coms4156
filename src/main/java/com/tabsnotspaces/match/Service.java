package com.tabsnotspaces.match;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a service offered by service providers.
 */
@Entity
public class Service {
    /**
     * The unique identifier for the service.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the service.
     */
    private String serviceName;

    /**
     * The set of service providers that offer this service.
     */
    @ManyToMany(mappedBy = "services")
    private Set<ServiceProvider> providers = new HashSet<>();

    // Constructors, getters, setters, and other fields

    /**
     * Get the unique identifier of the service.
     *
     * @return The service's unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the unique identifier of the service.
     *
     * @param id The service's unique identifier.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the service.
     *
     * @return The name of the service.
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Set the name of the service.
     *
     * @param serviceName The name of the service.
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Get the set of service providers that offer this service.
     *
     * @return The set of service providers.
     */
    public Set<ServiceProvider> getProviders() {
        return providers;
    }

    /**
     * Set the set of service providers that offer this service.
     *
     * @param providers The set of service providers.
     */
    public void setProviders(Set<ServiceProvider> providers) {
        this.providers = providers;
    }
}
