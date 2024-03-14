package com.dekapx.apps.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Value("${app.client-name}")
    private String clientId;
    @Value("${app.welcome-message}")
    private String message;

    @Override
    public String getMessage() {
        return this.message + "   " + clientId + "!";
    }
}
