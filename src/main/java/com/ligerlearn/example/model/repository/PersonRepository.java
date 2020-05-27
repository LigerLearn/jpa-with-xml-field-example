package com.ligerlearn.example.model.repository;

import com.ligerlearn.example.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
