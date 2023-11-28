package com.tabsnotspaces.match;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "service name should not be null")
    @NotEmpty(message = "service name should not be empty")
    private String serviceName;

    @Min(value = 1, message = "provider id should be greater than 0")
    private long providerId;


    /**
     * Default constructor for ServiceProvider.
     */
    public Service() {
        super();
    }

    /**
     * get service id
     *
     * @return id of service
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id for service
     *
     * @param id value to be set for service
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get providers of service
     *
     * @return gives set of all the providers of service
     */
    public long getProviderId() {
        return providerId;
    }

    /**
     * Set service providers set for all the service providers
     */
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
