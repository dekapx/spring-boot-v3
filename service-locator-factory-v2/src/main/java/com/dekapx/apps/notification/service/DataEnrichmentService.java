package com.dekapx.apps.notification.service;

import com.dekapx.apps.notification.model.EnrichmentModel;
import com.dekapx.apps.notification.model.NotificationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataEnrichmentService {
    public EnrichmentModel loadData(NotificationModel notificationModel) {
        log.info("DataEnrichmentService invoked...");
        return new EnrichmentModel();
    }
}
