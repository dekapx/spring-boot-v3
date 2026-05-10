package com.dekapx.apps.service;

import com.dekapx.apps.channel.SensorEventBuffer;
import com.dekapx.apps.entity.SensorReading;
import com.dekapx.apps.model.SensorReadingModel;
import com.dekapx.apps.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {
    private static final int BATCH_SIZE = 50;
    private static final long FLUSH_INTERVAL_MS = 100;

    private final SensorRepository sensorRepository;
    private final SensorEventBuffer sensorEventBuffer;

    @Scheduled(fixedDelay = FLUSH_INTERVAL_MS)
    public void flushBuffer() {
        List<SensorReadingModel> batch = new ArrayList<>(BATCH_SIZE);
        int drained = sensorEventBuffer.drainTo(batch, BATCH_SIZE);
        log.trace("Drained {} SensorReadings", drained);

        if (drained == 0) return;

        try {
            saveBatch(batch);
        } catch (Exception ex) {
            log.error("Failed to save batch of [{}] sensor readings: {}", batch.size(), ex.getMessage());
            handleFailedBatch(batch, ex);
        }
    }

    @Override
    public void saveBatch(List<SensorReadingModel> models) {
        List<SensorReading> entities = mapToEntities(models);
        this.sensorRepository.saveAll(entities);
        log.info("Saved sensor readings batch of size [{}]", entities.size());
    }

    @Async
    @Override
    public CompletableFuture<List<SensorReadingModel>> getAllReadings() {
        log.info("Fetching all sensor readings...");

        sleep(1000);

        List<SensorReading> sensorReadings = new ArrayList<>();
        this.sensorRepository.findAll().forEach(sensorReadings::add);
        List<SensorReadingModel> models = mapToModels(sensorReadings);
        return CompletableFuture.completedFuture(models);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {}
    }

    private List<SensorReadingModel> mapToModels(List<SensorReading> entities) {
        return entities.stream()
                .map(this::mapToModel)
                .toList();
    }

    private SensorReadingModel mapToModel(SensorReading entity) {
        return SensorReadingModel.builder()
                .sensorId(entity.getSensorId())
                .temperature(entity.getTemperature())
                .humidity(entity.getHumidity())
                .windSpeed(entity.getWindSpeed())
                .timestamp(entity.getTimestamp())
                .build();
    }

    private List<SensorReading> mapToEntities(List<SensorReadingModel> models) {
        return models.stream()
                .map(this::mapToEntity)
                .toList();
    }

    private SensorReading mapToEntity(SensorReadingModel model) {
        return SensorReading.builder()
                .sensorId(model.getSensorId())
                .temperature(model.getTemperature())
                .humidity(model.getHumidity())
                .windSpeed(model.getWindSpeed())
                .timestamp(model.getTimestamp())
                .build();
    }

    // TODO: implement retry logic, dead-letter queue, or alerting/metrics for failed batches
    private void handleFailedBatch(List<SensorReadingModel> batch, Exception ex) {
        log.warn("Handling failed batch of [{}] sensor readings: {}", batch.size(), ex.getMessage());
    }
}

