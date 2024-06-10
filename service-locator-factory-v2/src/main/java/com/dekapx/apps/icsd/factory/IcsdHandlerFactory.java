package com.dekapx.apps.icsd.factory;

import com.dekapx.apps.icsd.handler.IcsdHandler;

public interface IcsdHandlerFactory {
    IcsdHandler getIcsdHandler(String beanName);
}

