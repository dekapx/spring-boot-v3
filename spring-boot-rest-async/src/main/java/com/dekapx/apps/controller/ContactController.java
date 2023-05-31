package com.dekapx.apps.controller;

import com.dekapx.apps.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Autowired
    private ContactService contactService;

    @Operation(summary = "Contact Controller Ping")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Contact",
                    content = {@Content(mediaType = "application/json")
                    })
    })
    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ping() {
//        contactService.doSync();
        contactService.doAsync();
        return new ResponseEntity<>("SpringBoot REST Async API...", HttpStatus.OK);
    }
}
