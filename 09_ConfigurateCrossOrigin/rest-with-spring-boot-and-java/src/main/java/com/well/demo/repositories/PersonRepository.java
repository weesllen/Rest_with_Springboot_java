package com.well.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.well.demo.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long> {

}
