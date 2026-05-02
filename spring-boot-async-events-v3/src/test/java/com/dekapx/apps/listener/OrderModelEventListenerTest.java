package com.dekapx.apps.listener;

import com.dekapx.apps.event.SensorEvent;
import com.dekapx.apps.model.SensorReadingModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@Slf4j
@SpringBootTest
public class OrderModelEventListenerTest {
    @Autowired
    private TestApplicationContextAware applicationContextAware;

    @Test
    public void publishUserOnboardingEvent() {
        SensorEvent<SensorReadingModel> sensorEvent = prepareEvent(prepareModel());
        log.info("Publishing UserOnboardingEvent...");

        this.applicationContextAware.getApplicationContext().publishEvent(sensorEvent);
        log.info("UserOnboardingEvent published...");
    }

    private SensorEvent<SensorReadingModel> prepareEvent(SensorReadingModel model) {
        return new SensorEvent(this, model);
    }

    private SensorReadingModel prepareModel() {
        return SensorReadingModel.builder()
                .sensorId("SENSOR-001")
                .temperature(25.5)
                .humidity(60.0)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
