package com.dekapx.apps.notification.euroclear.handler;

import com.dekapx.apps.notification.model.NotificationModel;

public interface EuroclearApiHandler {
    void handleRequest(NotificationModel notificationModel);
}
