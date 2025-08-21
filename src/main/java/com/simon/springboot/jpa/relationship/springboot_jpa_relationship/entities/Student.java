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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "tbl_alumnos_cursos", //? nombre tabla intermedia
        joinColumns = @JoinColumn(name = "alumno_id"), //? Definir nombre de la PK de esta clase a considerar para la relacion
        inverseJoinColumns = @JoinColumn(name = "curso_id"), //? Definir nombre de la PK de la otra clase a relacionar
        uniqueConstraints = @UniqueConstraint(
            columnNames = {"alumno_id", "curso_id"}
        ) //? Con esto decimos, que no pueden haber registros de un mismo alumno con mis curso repetidos, deben ser unicos
    )
    private Set<Course> courses;

    public Student() {
        this.courses = new HashSet<>();
    }

    public Student(String name, String lastname) {
        this(); //llamar al constructor vacio 
        this.name = name;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Course {id=" + id + ", name=" + name + ", lastname=" + lastname + ", courses=" + courses + "}";
    }
}
