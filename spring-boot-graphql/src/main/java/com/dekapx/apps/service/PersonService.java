package com.dekapx.apps.service;

import com.dekapx.apps.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();

    Person findById(Long id);

    Person save(Person person);
}
