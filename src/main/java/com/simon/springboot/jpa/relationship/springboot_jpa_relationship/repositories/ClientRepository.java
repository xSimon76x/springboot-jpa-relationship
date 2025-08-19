package com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("select c from Client c left join fetch c.addresses where c.id=?1") //? para evitar usar esa propiedad en el aplication.properties
    Optional<Client> findOneWithAddresses(Long id);

    @Query("select c from Client c left join fetch c.invoices where c.id=?1") //? para evitar usar esa propiedad en el aplication.properties
    Optional<Client> findOneWithInvoices(Long id);

    @Query("select c from Client c left join fetch c.invoices left join fetch c.addresses where c.id=?1") //? para evitar usar esa propiedad en el aplication.properties
    Optional<Client> findOne(Long id);
}
