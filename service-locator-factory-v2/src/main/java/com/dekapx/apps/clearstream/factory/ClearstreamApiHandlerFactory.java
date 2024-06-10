package com.dekapx.apps.clearstream.factory;

import com.dekapx.apps.clearstream.handler.ClearstreamApiHandler;

public interface ClearstreamApiHandlerFactory {
    ClearstreamApiHandler getApiHandler(String beanName);
}
