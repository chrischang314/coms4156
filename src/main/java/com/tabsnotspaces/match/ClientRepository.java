package com.tabsnotspaces.match;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    public Optional<Client> findByClientNameIgnoreCase(String clientName);
}
