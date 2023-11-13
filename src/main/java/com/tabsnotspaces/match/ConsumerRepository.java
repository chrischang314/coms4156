package com.tabsnotspaces.match;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
    public List<Consumer> findByParentClientId(long parentClientId);

    public Optional<Consumer> findByParentClientIdAndConsumerNameIgnoreCase(long parentClientId, String consumerName);
}
