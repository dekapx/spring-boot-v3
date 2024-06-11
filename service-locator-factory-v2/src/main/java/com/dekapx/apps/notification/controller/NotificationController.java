package com.dekapx.apps.notification.controller;

import com.dekapx.apps.notification.model.NotificationModel;
import com.dekapx.apps.notification.service.DataEnrichmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NotificationController {

    public ResponseEntity<HttpStatus> receiveNotification(NotificationModel notificationModel) {
        log.info("Notification received...");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
