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

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping() {
        log.info("ContactController.ping() method invoked...");
        return new ResponseEntity<>("SpringBoot Redis Cache API...", HttpStatus.OK);
    }

    @GetMapping(value = "/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getContacts() {
        log.info("ContactController.getContacts() method invoked...");
        return new ResponseEntity<>(this.service.getContacts(), HttpStatus.OK);
    }
}
