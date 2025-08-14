package com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories;
import org.springframework.data.repository.CrudRepository;

import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

}
