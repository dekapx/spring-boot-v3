package com.dekapx.apps.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.client.AiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private AiClient aiClient;

    @GetMapping(value = "/info", produces = "application/json")
    public String getInfo() {
        log.info("ApplicationController.getInfo() invoked...");
        return "Spring Boot AI...";
    }

    @GetMapping("/ai/simple")
    public Completion completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return new Completion(aiClient.generate(message));
    }
}
