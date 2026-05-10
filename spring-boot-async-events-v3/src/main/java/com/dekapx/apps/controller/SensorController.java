package com.dekapx.apps.controller;

import com.dekapx.apps.event.SensorEvent;
import com.dekapx.apps.model.SensorReadingModel;
import com.dekapx.apps.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.dekapx.apps.common.ResourceUrls.BASE_URL;
import static com.dekapx.apps.common.ResourceUrls.INFO_URL;
import static com.dekapx.apps.common.ResourceUrls.SENSOR_URL;

@Slf4j
@RestController
@RequestMapping(BASE_URL)
@RequiredArgsConstructor
public class SensorController {
    private final ApplicationEventPublisher eventPublisher;
    private final SensorService sensorService;

    @Operation(summary = "Weather API Info")
    @GetMapping(INFO_URL)
    public String getInfo() {
        log.info("Weather API v1.0");
        return "Weather API v1.0";
    }

    @Operation(summary = "Register Sensor Reading")
    @PostMapping(SENSOR_URL)
    public ResponseEntity<SensorReadingModel> registerReading(@RequestBody SensorReadingModel model) {
        log.info("Registering sensor reading from Sensor ID: [{}]", model.getSensorId());
        this.eventPublisher.publishEvent(new SensorEvent(this, model));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @Operation(summary = "Get All Sensor Readings")
    @GetMapping(SENSOR_URL)
    public CompletableFuture<List<SensorReadingModel>> getAllReadingsAsync() {
        return this.sensorService.getAllReadings()
                .thenApply(data -> {
                    log.info("Non-blocking endpoint received async result: {}", data);
                    return data;
                })
                .exceptionally(ex -> {
                    log.error("Non-blocking data fetch failed: {}", ex.getMessage());
                    return Collections.emptyList();
                });
    }

}
