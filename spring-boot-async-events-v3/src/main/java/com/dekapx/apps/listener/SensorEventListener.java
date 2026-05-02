package com.dekapx.apps.listener;

import com.dekapx.apps.event.SensorEvent;
import com.dekapx.apps.model.SensorReadingModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SensorEventListener {
    @Async
    @Order(1)
    @EventListener
    public void processOrder(SensorEvent<SensorReadingModel> event) {
        final SensorReadingModel sensorReadingModel = event.getModel();
        log.info("Received SensorEvent: {}", sensorReadingModel.toString());
    }
}
