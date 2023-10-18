package com.tabsnotspaces.match;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;

import org.springframework.data.repository.CrudRepository;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
	public List<ServiceProvider> findByParentClientId(long parentClientId);
	public List<ServiceProvider> findByAvailability(Date availability);
}
