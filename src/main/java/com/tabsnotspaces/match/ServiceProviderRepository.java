package com.tabsnotspaces.match;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ServiceProviderRepository extends CrudRepository<ServiceProvider, Long> {
	public List<ServiceProvider> findByParentClientId(long parentClientId);
}
