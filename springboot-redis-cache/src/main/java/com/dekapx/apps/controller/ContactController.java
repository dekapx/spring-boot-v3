package com.dekapx.apps.controller;

import com.dekapx.apps.entity.Contact;
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

    @Operation(summary = "Contact Controller Ping")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Contact",
                    content = {@Content(mediaType = "application/json")
                    })
    })
    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping() {
        log.info("ContactController.ping() method invoked...");
        return new ResponseEntity<>("SpringBoot Redis Cache API...", HttpStatus.OK);
    }

    @Operation(summary = "Find Contact by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Contact",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Contact with ID [x] not found.", content = @Content)
    })
    @GetMapping(value = "/contact/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> findContactById(@PathVariable Long id) {
        log.info("Find Contact for ID [{}]", id);
        final Contact contact = this.service.findById(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
}
