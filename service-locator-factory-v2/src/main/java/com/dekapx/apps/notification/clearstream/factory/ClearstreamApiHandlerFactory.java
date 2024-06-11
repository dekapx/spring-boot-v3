package com.dekapx.apps.notification.clearstream.factory;

import com.dekapx.apps.notification.clearstream.handler.ClearstreamApiHandler;

public interface ClearstreamApiHandlerFactory {
    ClearstreamApiHandler getApiHandler(String beanName);
}
