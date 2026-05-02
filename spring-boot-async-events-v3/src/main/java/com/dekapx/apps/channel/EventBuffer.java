package com.dekapx.apps.channel;

import com.dekapx.apps.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
public class EventBuffer {
    private final BlockingQueue<OrderModel> buffer =
            new LinkedBlockingQueue<>(10_000);

    public boolean offer(OrderModel model) {
        boolean accepted = buffer.offer(model);
        if (!accepted) {
            // TODO: Metric/alert: queue full — consider dead-letter or retry
            log.warn("Event buffer full, dropping event");
        }
        return accepted;
    }

    public int drainTo(List<OrderModel> models, int maxElements) {
        return buffer.drainTo(models, maxElements);
    }

    public int size() {
        return buffer.size();
    }
}
