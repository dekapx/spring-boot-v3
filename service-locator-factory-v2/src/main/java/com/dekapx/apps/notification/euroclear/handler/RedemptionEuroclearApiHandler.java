package com.dekapx.apps.notification.euroclear.handler;

import com.dekapx.apps.notification.euroclear.client.EuroclearFeignclient;
import com.dekapx.apps.notification.model.NotificationModel;
import com.dekapx.apps.notification.service.DataEnrichmentService;
import com.dekapx.apps.notification.util.XmlFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedemptionEuroclearApiHandler implements EuroclearApiHandler {
    @Autowired
    private DataEnrichmentService dataEnrichmentService;
    @Autowired
    private XmlFileWriter xmlFileWriter;
    @Autowired
    private EuroclearFeignclient feignClient;

    @Override
    public void handleRequest(NotificationModel notificationModel) {
        this.dataEnrichmentService.loadData(notificationModel);
        this.xmlFileWriter.write("test contents");
        this.feignClient.callCpi();
    }
}
