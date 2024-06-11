package com.dekapx.apps.notification.clearstream.handler;

import com.dekapx.apps.notification.clearstream.client.ClearstreamFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedemptionClearstreamApiHandler implements ClearstreamApiHandler {
    @Autowired
    private ClearstreamFeignClient feignClient;

    @Override
    public void handleRequest() {
        this.feignClient.callCpi();
    }
}
