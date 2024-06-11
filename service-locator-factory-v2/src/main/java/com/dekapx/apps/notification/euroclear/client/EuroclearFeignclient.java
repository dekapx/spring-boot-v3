package com.dekapx.apps.notification.euroclear.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EuroclearFeignclient {
    public void callCpi() {
        log.info("ClearstreamFeignClient API invoked...");
    }
}
