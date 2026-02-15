package com.dekapx.apps.controller;

import lombok.extern.slf4j.Slf4j;
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
    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping() {
        log.info("ContactController.ping() method invoked...");
        return new ResponseEntity<>("SpringBoot Redis Cache API...", HttpStatus.OK);
    }
}
