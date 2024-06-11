package com.dekapx.apps.notification.clearstream.handler;

import com.dekapx.apps.notification.clearstream.client.ClearstreamFeignClient;
import com.dekapx.apps.notification.model.NotificationModel;
import com.dekapx.apps.notification.service.DataEnrichmentService;
import com.dekapx.apps.notification.util.XmlFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedemptionClearstreamApiHandler implements ClearstreamApiHandler {
    @Autowired
    private DataEnrichmentService dataEnrichmentService;
    @Autowired
    private XmlFileWriter xmlFileWriter;
    @Autowired
    private ClearstreamFeignClient feignClient;

    @Override
    public void handleRequest(NotificationModel notificationModel) {
        this.dataEnrichmentService.loadData(notificationModel);
        this.xmlFileWriter.write("test contents");
        this.feignClient.callCpi();
    }
}
