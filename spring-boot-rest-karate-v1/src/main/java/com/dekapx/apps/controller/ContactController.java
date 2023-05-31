package com.dekapx.apps.controller;

import com.dekapx.apps.model.Contact;
import com.dekapx.apps.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Operation(summary = "Find Contact by id")
    @GetMapping(value = "/contacts/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> findContactById(@PathVariable Long id) {
        log.info("Find Contact for ID [{}]", id);
        final Contact contact = this.contactService.findById(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @Operation(summary = "Find All Contacts")
    @GetMapping(value = "/contacts",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contact>> findAll() {
        log.info("Find all contacts");
        final List<Contact> contacts = this.contactService.findAll();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @Operation(summary = "Create new contact", tags = {"contact"})
    @PostMapping(value = "/contacts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> create(@RequestBody Contact contact) {
        log.info("Create new contact...");
        return new ResponseEntity<>(this.contactService.save(contact), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing contact", description = "", tags = {"contact"})
    @PutMapping(value = "/contacts/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody Contact contact) {
        log.info("Update contact for ID [{}]...", id);
        return new ResponseEntity<>(this.contactService.update(id, contact), HttpStatus.OK);
    }
}
