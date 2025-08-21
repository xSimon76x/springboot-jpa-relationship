package com.simon.springboot.jpa.relationship.springboot_jpa_relationship.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.simon.springboot.jpa.relationship.springboot_jpa_relationship.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

    //? Para evitar la carga peresosa al obtener los cursos de un estudiante, daria error de lazily si no esta esto
    @Query("select s from Student s left join fetch s.courses where s.id=?1")
    Optional<Student> findOneWithCourses(Long id);

}
