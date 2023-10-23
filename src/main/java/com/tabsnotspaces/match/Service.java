package com.tabsnotspaces.match;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "services")
    private Set<ServiceProvider> providers = new HashSet<>();

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
    public Set<ServiceProvider> getProviders() {
        return providers;
    }

    /**
     * Set service providers set for all the service providers
     * @param providers
     */
    public void setProviders(Set<ServiceProvider> providers) {
        this.providers = providers;
    }
}
