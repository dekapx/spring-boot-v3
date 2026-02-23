package com.dekapx.apps.listener;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class OrderEventListener {
    private final AtomicLong processedCount = new AtomicLong(0);
    private final AtomicLong failedCount = new AtomicLong(0);

    @Async
    @EventListener
    public void handleOrderPlacedEvent(OrderEvent<Order> event) {
        final Order order = event.getModel();
        try {
            log.info("Received OrderEvent for order: {} & Product {}",
                    order.getOrderId(),
                    order.getProductName());
            processedCount.incrementAndGet();
        } catch (Exception ex) {
            failedCount.incrementAndGet();
            log.error("Failed to process event: {}", order.getOrderId(), ex);
        }
    }

    public long getProcessedCount() {
        return processedCount.get();
    }

    public long getFailedCount() {
        return failedCount.get();
    }
}
