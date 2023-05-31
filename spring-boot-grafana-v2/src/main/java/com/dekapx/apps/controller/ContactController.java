package com.dekapx.apps.controller;

import com.dekapx.apps.entity.Contact;
import com.dekapx.apps.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ContactController {
    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(value = "/info", produces = "application/json")
    public ResponseEntity<String> getInfo() {
        log.info("get Info...");
        return ResponseEntity.ok().body("Spring Boot Grafana... OK");
    }

    @GetMapping(value = "/contact/{id}", produces = "application/json")
    public Contact findById(@PathVariable Long id) {
        return contactService.findById(id);
    }
}
