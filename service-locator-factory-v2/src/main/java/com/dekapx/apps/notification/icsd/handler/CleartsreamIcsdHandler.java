package com.dekapx.apps.notification.icsd.handler;

import com.dekapx.apps.notification.clearstream.factory.ClearstreamApiHandlerFactory;
import com.dekapx.apps.notification.clearstream.handler.ClearstreamApiHandler;
import com.dekapx.apps.notification.model.NotificationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CleartsreamIcsdHandler implements IcsdHandler {
    @Autowired
    private ClearstreamApiHandlerFactory clearstreamApiHandlerFactory;

    @Override
    public void invokeHandler(NotificationModel notificationModel) {
        log.info("Invoke CleartsreamIcsdHandler...");
        ClearstreamApiHandler apiHandler = this.clearstreamApiHandlerFactory.getApiHandler("redemptionClearstreamApiHandler");
        apiHandler.handleRequest(notificationModel);

    }
}
