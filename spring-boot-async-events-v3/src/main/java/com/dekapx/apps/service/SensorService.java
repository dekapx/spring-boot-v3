package com.dekapx.apps.service;

import com.dekapx.apps.model.SensorReadingModel;

import java.util.List;

public interface SensorService {
    void flushBuffer();
    void saveBatch(List<SensorReadingModel> models);
}
