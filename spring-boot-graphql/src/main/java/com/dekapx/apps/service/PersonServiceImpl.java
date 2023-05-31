package com.dekapx.apps.service;

import com.dekapx.apps.exception.ResourceNotFoundException;
import com.dekapx.apps.entity.Person;
import com.dekapx.apps.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private PersonRepository repository;

    @Override
    public List<Person> findAll() {
        final List<Person> persons = new ArrayList<>();
        this.repository.findAll().forEach(persons::add);
        return persons;
    }

    @Override
    public Person findById(final Long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Person with ID [%d] not found.", id)));
    }

    @Override
    public Person save(final Person person) {
        return this.repository.save(person);
    }
}
