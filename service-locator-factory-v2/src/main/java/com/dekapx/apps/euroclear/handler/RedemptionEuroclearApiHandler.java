package com.dekapx.apps.euroclear.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedemptionEuroclearApiHandler implements EuroclearApiHandler {
    @Override
    public void handleRequest() {
        log.info("RedemptionEuroclearApiHandler invoked...");
    }
}
