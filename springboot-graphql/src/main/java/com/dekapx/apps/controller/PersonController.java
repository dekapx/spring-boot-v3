package com.dekapx.apps.controller;

import com.dekapx.apps.model.Person;
import com.dekapx.apps.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PersonController {
    @Autowired
    private PersonService personService;

    @SchemaMapping(typeName = "Query",value = "persons")
    public List<Person> findAll() {
        return this.personService.findAll();
    }


    @SchemaMapping(typeName = "Query",value = "personById")
    public Person findOne(@Argument(name = "id") Long id) {
        return this.personService.findById(id);
    }
}
