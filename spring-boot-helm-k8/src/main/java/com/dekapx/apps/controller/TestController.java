package com.dekapx.apps.controller;

import com.dekapx.apps.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dekapx.apps.common.ResourceUrls.BASE_URL;
import static com.dekapx.apps.common.ResourceUrls.INFO_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping(INFO_URL)
    public String getInfo() {
        log.info("TestController...");
        return testService.getMessage();
    }
}
