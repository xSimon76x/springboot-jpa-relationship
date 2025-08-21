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
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Course;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Invoice;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Student;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.ClientDetailsRepository;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.ClientRepository;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.CourseRepository;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.InvoiceRepository;
import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToManyBidireccionalRemoveById();
	}

	//* Para profundizar mas ingresar a la web de hibernate.org */
	//* https://docs.jboss.org/hibernate/orm/7.1/userguide/html_single/Hibernate_User_Guide.html */

	@Transactional
	public void manyToManyBidireccionalRemoveById() {
		
		manyToManyBidireccionalFindById();

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(1L);

		if (studentOptionalDB.isPresent()) {
			Student studentDB = studentOptionalDB.get();

			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(1L);

			if (courseOptionalDb.isPresent()) {
				Course courseDB = courseOptionalDb.get();
				// studentDB.getCourses().remove(courseDB);
				studentDB.removeCourse(courseDB);
				studentRepository.save(studentDB);
				System.out.println(studentDB);
			}
		}
	}

	@Transactional
	public void manyToManyBidireccionalFindById() {
		Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findOneWithStudents(1L).get(); 
		Course course2 = courseRepository.findOneWithStudents(2L).get();

		// student1.setCourses(Set.of(course1, course2));
		// student2.setCourses(Set.of(course2));
		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	@Transactional
	public void manyToManyBidireccionalRemove() {
		manyToManyBidireccional();

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(3L);

		if (studentOptionalDB.isPresent()) {
			Student studentDB = studentOptionalDB.get();

			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(3L);

			if (courseOptionalDb.isPresent()) {
				Course courseDB = courseOptionalDb.get();
				// studentDB.getCourses().remove(courseDB);
				studentDB.removeCourse(courseDB);
				studentRepository.save(studentDB);
				System.out.println(studentDB);
			}
		}


	}

	@Transactional
	public void manyToManyBidireccional() {
		
		Student student1 = new Student("Jano","Pura");
		Student student2 = new Student("Elba","Dore");
		Course course1 =  new Course("Curso de Java Master", "Andres"); 
		Course course2 =  new Course("Curso de Spring Boot", "Andres"); 

		// student1.setCourses(Set.of(course1, course2));
		// student2.setCourses(Set.of(course2));
		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);


	}

	@Transactional
	public void manyToManyRemove() {

		manyToMany();

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(3L);

		if (studentOptionalDB.isPresent()) {
			Student studentDB = studentOptionalDB.get();

			Optional<Course> courseOptionalDb = courseRepository.findById(3L);

			if (courseOptionalDb.isPresent()) {
				Course courseDB = courseOptionalDb.get();
				studentDB.getCourses().remove(courseDB);
				studentRepository.save(studentDB);
				System.out.println(studentDB);
			}
		}


	}

	@Transactional
	public void manyToManyRemoveById() {

		manyToManyFindById();

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(1L);

		if (studentOptionalDB.isPresent()) {
			Student studentDB = studentOptionalDB.get();

			Optional<Course> courseOptionalDb = courseRepository.findById(2L);

			if (courseOptionalDb.isPresent()) {
				Course courseDB = courseOptionalDb.get();
				studentDB.getCourses().remove(courseDB);
				studentRepository.save(studentDB);
				System.out.println(studentDB);
			}
		}


	}

	@Transactional
	public void manyToManyFindById() {

		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get(); 
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	@Transactional
	public void manyToMany() {

		Student student1 = new Student("Jano","Pura");
		Student student2 = new Student("Elba","Dore");
		Course course1 =  new Course("Curso de Java Master", "Andres"); 
		Course course2 =  new Course("Curso de Spring Boot", "Andres"); 

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
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
