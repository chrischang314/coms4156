package com.tabsnotspaces.match;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    public List<Review> findByConsumerId(long consumerId);

    public List<Review> findByServiceProviderId(long serviceProviderId);
}
