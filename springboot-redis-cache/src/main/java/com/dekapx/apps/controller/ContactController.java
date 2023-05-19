package com.dekapx.apps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
}
