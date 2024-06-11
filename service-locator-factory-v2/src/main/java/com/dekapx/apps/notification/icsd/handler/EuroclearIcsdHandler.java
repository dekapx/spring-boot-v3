package com.dekapx.apps.notification.icsd.handler;

import com.dekapx.apps.notification.euroclear.factory.EuroclearApiHandlerFactory;
import com.dekapx.apps.notification.euroclear.handler.EuroclearApiHandler;
import com.dekapx.apps.notification.model.NotificationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EuroclearIcsdHandler implements IcsdHandler {
    @Autowired
    private EuroclearApiHandlerFactory euroclearApiHandlerFactory;

    @Override
    public void invokeHandler(NotificationModel notificationModel) {
        log.info("Invoke EuroclearIcsdHandler...");

        EuroclearApiHandler apiHandler = this.euroclearApiHandlerFactory.getApiHandler("redemptionEuroclearApiHandler");
        apiHandler.handleRequest(notificationModel);
    }
}
