package com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    public Client() {
        addresses = new ArrayList<>(); // inicializar el address como un array vacio
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

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", lastname=" + lastname + ", addresses=" + addresses + "}";
    } 

    

}
