package com.tabsnotspaces.match;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String serviceName;
    private long providerId;


    /**
     * Default constructor for ServiceProvider.
     */
    public Service() {
        super();
    }

    /**
     * get service id
     * @return id of service
     */
    public Long getId() {
        return id;
    }

    /**
     * Set id for service
     * @param id value to be set for service
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get providers of service
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
