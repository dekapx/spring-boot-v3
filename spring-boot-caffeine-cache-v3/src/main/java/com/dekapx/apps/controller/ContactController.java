package com.dekapx.apps.controller;

import com.dekapx.apps.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ContactController {
    private final ContactService service;

    @Autowired
    public ContactController(final ContactService service) {
        this.service = service;
    }

    @GetMapping(value = "/update")
    public ResponseEntity<String> update() {
        log.info("Update cache entry..");
        this.service.update();
        return new ResponseEntity<>("Update cache entry", HttpStatus.OK);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllContacts() {
        log.info("Get all contacts...");
        return new ResponseEntity<>(this.service.getAllContacts(), HttpStatus.OK);
    }

    @GetMapping(value = "/inactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInactiveContacts() {
        log.info("Get inactive contacts...");
        return new ResponseEntity<>(this.service.getInactiveContacts(), HttpStatus.OK);
    }
}
