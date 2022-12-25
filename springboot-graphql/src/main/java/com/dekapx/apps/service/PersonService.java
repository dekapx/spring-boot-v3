package com.dekapx.apps.service;

import com.dekapx.apps.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PersonService {
    List<Person> findAll();
    Person findById(Long id);
}
