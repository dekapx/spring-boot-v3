package com.dekapx.apps.controller;

import com.dekapx.apps.model.Person;
import com.dekapx.apps.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PersonController {
    @Autowired
    private PersonService personService;

    @QueryMapping(value = "persons")
    public List<Person> findAll() {
        return this.personService.findAll();
    }
}
