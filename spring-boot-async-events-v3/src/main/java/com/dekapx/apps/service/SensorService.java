package com.dekapx.apps.service;

import com.dekapx.apps.model.SensorReadingModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SensorService {
    void saveBatch(List<SensorReadingModel> models);
    CompletableFuture<List<SensorReadingModel>> getAllReadings();
}
