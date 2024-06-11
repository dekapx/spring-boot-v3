package com.dekapx.apps.notification.euroclear.handler;

import com.dekapx.apps.notification.euroclear.client.EuroclearFeignclient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedemptionEuroclearApiHandler implements EuroclearApiHandler {
    @Autowired
    private EuroclearFeignclient feignclient;

    @Override
    public void handleRequest() {
        this.feignclient.callCpi();
    }
}
