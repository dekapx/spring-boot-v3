package com.dekapx.apps.service;

import com.dekapx.apps.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Override
    public List<Person> findAll() {
        Person person = new Person();
        person.setId(1L);
        person.setName("Test Person");
        return List.of(person);
    }

    @Override
    public Person findById(Long id) {
        Person person = new Person();
        person.setId(2L);
        person.setName("Dummy User");
        return person;
    }
}
