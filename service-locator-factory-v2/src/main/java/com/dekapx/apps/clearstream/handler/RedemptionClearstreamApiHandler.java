package com.dekapx.apps.clearstream.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedemptionClearstreamApiHandler implements ClearstreamApiHandler {
    @Override
    public void handleRequest() {
        log.info("RedemptionClearstreamApiHandler invoked...");
    }
}
