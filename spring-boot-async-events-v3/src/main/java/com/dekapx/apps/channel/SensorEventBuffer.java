package com.dekapx.apps.channel;

import com.dekapx.apps.model.SensorReadingModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorEventBuffer {
    private static final int QUEUE_SIZE = 10_000;

    private final BlockingQueue<SensorReadingModel> buffer =
            new LinkedBlockingQueue<>(QUEUE_SIZE);

    public boolean offer(SensorReadingModel model) {
        boolean accepted = buffer.offer(model);
        if (!accepted) {
            log.warn("Buffer is full. Sensor reading [{}] added to Dead Letter Queue", model.toString());
        }
        return accepted;
    }

    public int drainTo(List<SensorReadingModel> models, int maxElements) {
        return buffer.drainTo(models, maxElements);
    }

    public int size() {
        return buffer.size();
    }
}
