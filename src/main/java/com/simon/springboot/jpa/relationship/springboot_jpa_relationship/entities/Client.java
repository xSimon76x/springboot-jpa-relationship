package com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    //El orphanRemoval borra todos los registros de address huerfanos, que quedaron sin relacion hacia cliente
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true) // Es para que cuando se elimine/Inserte un cliente tambien necesite crear o eliminar la direccion
    // @JoinColumn( name = "client_id") // Con esto, en la tabla Address quedara una forengkey apuntando a esta tabla clients
    @JoinTable(
        name = "tbl_clientes_to_direcciones", //? nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "id_cliente"), //? nombre de la FK en la tabla intermedia tbl_clientes_to_direcciones
        inverseJoinColumns = @JoinColumn(name = "id_direcciones"), //? PK de la tabla de direcciones que estara enlazada a esta tabla intermedia
        uniqueConstraints = @UniqueConstraint( columnNames = {"id_direcciones"}) //? PK de la tabla intermedia tbl_clientes_to_direcciones
    )
    private Set<Address> addresses;

    @OneToMany( 
        cascade = CascadeType.ALL, 
        orphanRemoval = true, 
        mappedBy = "client" //? Este "client", es del atributo que tiene Invoice en su clase, para hacer la relacion
    )
    private Set<Invoice> invoices;

    public Client() {
        addresses = new HashSet<>(); // inicializar el address como un array vacio
        invoices = new HashSet<>(); // inicializar el address como un array vacio
    }

    public Client(String name, String lastname) {
        this(); //llamar al constructor vacio
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Client addInvoice(Invoice invoice){
        invoices.add(invoice);
        invoice.setClient(this);
        return this;
    }

    @Override
    public String toString() {
        return "{id=" + id + 
            ", name=" + name + 
            ", lastname=" + lastname + 
            ", invoices=" + invoices 
            // ", addresses=" + addresses + 
            + "}";
    }

    public static Object builder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    } 

    

}
