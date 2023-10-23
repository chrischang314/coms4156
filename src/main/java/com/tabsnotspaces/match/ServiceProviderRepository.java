package com.tabsnotspaces.match;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
	// You can add custom query methods here if needed
    public List<ServiceProvider> findByParentClientId(long parentClientId);
    public List<ServiceProvider> findByAvailabilities(TupleDateTime tupleDateTime);
}
