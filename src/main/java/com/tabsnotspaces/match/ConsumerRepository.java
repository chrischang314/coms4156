package com.tabsnotspaces.match;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
	public List<Consumer> findByParentClientId(long parentClientId);
}
