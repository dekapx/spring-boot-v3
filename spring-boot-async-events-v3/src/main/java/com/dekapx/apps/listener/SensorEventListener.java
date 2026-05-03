package com.dekapx.apps.listener;

import com.dekapx.apps.channel.SensorEventBuffer;
import com.dekapx.apps.event.SensorEvent;
import com.dekapx.apps.model.SensorReadingModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorEventListener {
    private final SensorEventBuffer sensorEventBuffer;

    @Async
    @EventListener
    public void processOrder(SensorEvent<SensorReadingModel> event) {
        final SensorReadingModel sensorReadingModel = event.getModel();
        log.info("---------------- Received SensorEvent: [{}] ----------------", sensorReadingModel.toString());
        this.sensorEventBuffer.offer(sensorReadingModel);
    }
}
