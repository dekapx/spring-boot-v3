package com.dekapx.apps.notification.euroclear.factory;

import com.dekapx.apps.notification.euroclear.handler.EuroclearApiHandler;

public interface EuroclearApiHandlerFactory {
    EuroclearApiHandler getApiHandler(String beanName);
}
