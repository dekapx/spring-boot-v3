package com.dekapx.apps.notification.icsd.factory;


import com.dekapx.apps.notification.icsd.handler.IcsdHandler;

public interface IcsdHandlerFactory {
    IcsdHandler getIcsdHandler(String beanName);
}

