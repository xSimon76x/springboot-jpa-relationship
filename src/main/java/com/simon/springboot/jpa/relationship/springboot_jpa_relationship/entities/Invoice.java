package com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Long total;

    //? Se leeria como, muchas facturas hacia 1 solo cliente
    @ManyToOne
    //* Se usa para definir nombres de columnas que son llaves foraneas
    //* Si se cambia este name, saldran varios errores, lo mejor seria 
    //* eliminar el campo o la tabla y dejar que el spring los cree denuevo pero 
    //* con el nuevo nombre que definimos  
    @JoinColumn(name = "id_cliente_temp") //? por defecto si no se define, seria el nombre de la variable concatenado con "_id" -> client_id
    private Client client;

    public Invoice() {}

    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", description=" + description + ", total=" + total + ", client=" + client + "}";
    }
    
}
