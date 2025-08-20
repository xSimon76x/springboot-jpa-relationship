package com.simon.springboot.jpa.relationship.springboot_jpa_relationship;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Address;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Client;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.ClientDetails;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Invoice;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.ClientDetailsRepository;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.ClientRepository;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository; 

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		oneToOneBidireccionalFindById();
	}

	@Transactional
	public void oneToOneBidireccionalFindById() {
		Optional<Client> client = clientRepository.findOne(1L);

		client.ifPresent(c -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);
	
			c.setClientDetails(clientDetails);
			clientDetails.setClient(c);
	
			clientRepository.save(c);
	
			System.out.println(c);
		});

	}

	@Transactional
	public void oneToOneBidireccional() {
		Client client = new Client("Erba","Pura");

		ClientDetails clientDetails = new ClientDetails(true, 5000);

		client.setClientDetails(clientDetails);
		clientDetails.setClient(client);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToOneFindById() {

		Optional<Client> client = clientRepository.findById(2L);

		client.ifPresent( c -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);
	
			clientDetails.setClient(c);
			clientDetailsRepository.save(clientDetails);
			System.out.println(c);
		});

	}

	@Transactional
	public void oneToOne() {

		Client client = new Client("Erba", "Pura");
		clientRepository.save(client);

		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetails.setClient(client);
		clientDetailsRepository.save(clientDetails);

	}

	@Transactional
	public void removeInvoiceBidireccionalFindById() {

		Optional<Client> client = clientRepository.findOne(1L);
		
		client.ifPresent( c -> {
			
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de oficina", 3230L);
			
			c.addInvoice(invoice1).addInvoice(invoice2);
			
			clientRepository.save(c);
			
			System.out.println(c);
			
		});

		Optional<Client> optionalClientDB = clientRepository.findOne(1L);

		optionalClientDB.ifPresent(clientDB -> {
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L);
			invoiceOptional.ifPresent(invoice -> {
				// Obtenemos las facturas del cliente, y las borramos
				clientDB.getInvoices().remove(invoice);
				// de las facturas borradas, se dejan sin un cliente asociado
				invoice.setClient(null);
				// se guardan los cambios en el cliente, ya que el tiene las relaciones definidas
				clientRepository.save(clientDB);
				System.out.println(clientDB);
			});
		});
	}
	
	@Transactional
	public void oneToManyInvoiceBidireccionalFindById() {

		Optional<Client> client = clientRepository.findOne(1L);

		client.ifPresent( c -> {
			
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de oficina", 3230L);

			c.addInvoice(invoice1).addInvoice(invoice2);

			clientRepository.save(c);

			System.out.println(c);
			
		});
	}

	@Transactional
	public void oneToManyInvoiceBidireccional() {

		
		
		Client client = new Client("Fran", "Moras");
        Invoice invoices1 = new Invoice("compras de la casa", 5000L);
        Invoice invoices2 = new Invoice("compras de la oficina", 8000L);
		invoices1.setClient(client);
		invoices2.setClient(client);

		Set<Invoice> invoicesSet = new HashSet<>();
		invoicesSet.add(invoices1);
		invoicesSet.add(invoices2);
		client.setInvoices(invoicesSet);
 
		Client clientDB = clientRepository.save(client);
        System.out.printf("Result Client: %s", clientDB);  
	}

	@Transactional
	public void removeAddressFindById() {
		// Client client = new Client("Fran", "Moras");
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresent( client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de gama", 5928);
		
			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);
	
			clientRepository.save(client);
	
			System.out.println(client);

			//*Para evitar recarga tardia del getAddresses y que de error el remove(), se usa el findOne 
			//*Que es un query personalizada */
			Optional<Client> optionalClient2 = clientRepository.findOneWithAddresses(2L); 
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(0);
				clientRepository.save(c);
				System.out.println(c);
			});

		});

	}

	@Transactional
	public void removeAddress() {
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de gama", 5928);
	
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(0);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void oneToManyFindById() {
		// Client client = new Client("Fran", "Moras");
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresent( client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de gama", 5928);
		
			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);
	
			clientRepository.save(client);
	
			System.out.println(client);
		});

	}

	@Transactional
	public void oneToMany() {
		Client client = new Client("Fran", "Moras");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de gama", 5928);
	
		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void manyToOne() {
		Client client = new Client("John", "Doe");
		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);

		clientRepository.save(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);

		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByIdClient() {
		Optional<Client> optionalClient = clientRepository.findById(1L);

		if ( optionalClient.isPresent()){
			Client client = optionalClient.orElseThrow();
			Invoice invoice = new Invoice("herramientas de oficina", 5123L);
			invoice.setClient(client);
	
			clientRepository.save(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
	
			System.out.println(invoiceDB);
		}

	}

}
