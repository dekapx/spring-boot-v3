package com.dekapx.apps.euroclear.factory;

import com.dekapx.apps.euroclear.handler.EuroclearApiHandler;

public interface EuroclearApiHandlerFactory {
    EuroclearApiHandler getApiHandler(String beanName);
}
