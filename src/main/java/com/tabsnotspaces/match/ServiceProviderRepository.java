package com.tabsnotspaces.match;

import java.util.List;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
	public List<ServiceProvider> findByParentClientId(long parentClientId);
	public List<ServiceProvider> findByAvailabilities(TupleDateTime tupleDateTime);
}
