package com.tabsnotspaces.match;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
    public List<ServiceProvider> findByParentClientId(long parentClientId);

    public List<ServiceProvider> findByAvailabilities(TupleDateTime tupleDateTime);

    public Optional<ServiceProvider> findByParentClientIdAndProviderNameIgnoreCase(long parentClientId, String providerName);
}
