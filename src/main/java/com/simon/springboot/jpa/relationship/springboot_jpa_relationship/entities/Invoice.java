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
        //? para relacion que hay entre invoice y client, no puede haber en el toString
        //? una invocacion de sus datos en ambos lados, solo debe estar en un lado
        return "{id=" + id + ", description=" + description + ", total=" + total + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((total == null) ? 0 : total.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Invoice other = (Invoice) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (total == null) {
            if (other.total != null)
                return false;
        } else if (!total.equals(other.total))
            return false;
        return true;
    }
    
    
}
