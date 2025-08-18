package com.simon.springboot.jpa.relationship.springboot_jpa_relationship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Address;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Client;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Invoice;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.ClientRepository;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		oneToManyInvoiceBidireccional();
	}

	@Transactional
	public void oneToManyInvoiceBidireccional() {
		Client client = new Client("Fran", "Moras");

		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de la oficina", 8000L);

		List<Invoice> invoices = new ArrayList<>();
		invoices.add(invoice1); 
		invoices.add(invoice2);
		client.setInvoices(invoices);
		
		invoice1.setClient(client);
		invoice2.setClient(client);
		
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void removeAddressFindById() {
		// Client client = new Client("Fran", "Moras");
		Optional<Client> optionalClient = clientRepository.findById(2L);

		optionalClient.ifPresent( client -> {
			Address address1 = new Address("El verjel", 1234);
			Address address2 = new Address("Vasco de gama", 5928);
		
			client.setAddresses(Arrays.asList(address1, address2));
	
			clientRepository.save(client);
	
			System.out.println(client);

			//*Para evitar recarga tardia del getAddresses y que de error el remove(), se usa el findOne 
			//*Que es un query personalizada */
			Optional<Client> optionalClient2 = clientRepository.findOne(2L); 
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
		
			client.setAddresses(Arrays.asList(address1, address2));
	
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
