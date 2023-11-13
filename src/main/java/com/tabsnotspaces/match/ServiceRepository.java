package com.tabsnotspaces.match;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends CrudRepository<Service, Long> {
    public Optional<Service> findByProviderIdAndServiceNameIgnoreCase(long providerId, String serviceName);
}
