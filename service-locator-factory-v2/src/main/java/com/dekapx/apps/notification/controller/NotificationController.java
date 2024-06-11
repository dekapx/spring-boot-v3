package com.dekapx.apps.notification.controller;

import com.dekapx.apps.notification.icsd.factory.IcsdHandlerFactory;
import com.dekapx.apps.notification.model.NotificationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class NotificationController {
    @Autowired
    private IcsdHandlerFactory icsdHandlerFactory;

    @GetMapping(value = "/info", produces = "application/json")
    public String info() {
        log.info("NotificationController.info() method invoked...");
        this.icsdHandlerFactory.invokeIcsdHandlers(new NotificationModel("Redemption"));
        return "NotificationController...";
    }

    public ResponseEntity<HttpStatus> receiveNotification(NotificationModel notificationModel) {
        log.info("Notification received...");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
