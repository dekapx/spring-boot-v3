package com.dekapx.apps.notification.icsd.factory;


import com.dekapx.apps.notification.icsd.handler.IcsdHandler;
import com.dekapx.apps.notification.model.NotificationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class IcsdHandlerFactoryImpl implements IcsdHandlerFactory {
    private List<IcsdHandler> icsdHandlers;

    @Autowired
    public IcsdHandlerFactoryImpl(List<IcsdHandler> icsdHandlers) {
        this.icsdHandlers = new ArrayList<>();
        this.icsdHandlers.addAll(icsdHandlers);
    }

    @Override
    public void invokeIcsdHandlers(NotificationModel notificationModel) {
        log.info("Notifying handlers");
        this.icsdHandlers.forEach(icsdHandler -> icsdHandler.invokeHandler(notificationModel));
    }
}
