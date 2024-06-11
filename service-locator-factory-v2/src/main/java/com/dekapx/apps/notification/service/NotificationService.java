package com.dekapx.apps.notification.service;

import com.dekapx.apps.notification.icsd.factory.IcsdHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private DataEnrichmentService dataEnrichmentService;
    @Autowired
    private IcsdHandlerFactory icsdHandlerFactory;

}
