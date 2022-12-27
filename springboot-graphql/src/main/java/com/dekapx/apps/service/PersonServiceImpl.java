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
        person.setFirstName("Test");
        person.setLastName("Person");
        person.setPhone("+1 111 222 3344");
        person.setEmail("test.person@dummy.com");
        return List.of(person);
    }

    @Override
    public Person findById(Long id) {
        Person person = new Person();
        person.setId(id);
        person.setFirstName("Test");
        person.setLastName("Person");
        person.setPhone("+1 111 222 3344");
        person.setEmail("test.person@dummy.com");
        return person;
    }
}
