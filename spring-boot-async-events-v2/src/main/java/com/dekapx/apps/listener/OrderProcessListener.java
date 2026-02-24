package com.dekapx.apps.listener;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
public class OrderProcessListener {
    private final AtomicLong processedCount = new AtomicLong(0);
    private final AtomicLong failedCount = new AtomicLong(0);

    @Async
    @Order(1)
    @EventListener
    public void processOrder(OrderEvent<OrderModel> event) {
        final OrderModel orderModel = event.getModel();
        try {
            log.info("Received OrderEvent for order: {} & Product {}",
                    orderModel.getOrderId(),
                    orderModel.getProductName());
            processedCount.incrementAndGet();
        } catch (Exception ex) {
            failedCount.incrementAndGet();
            log.error("Failed to process event: {}", orderModel.getOrderId(), ex);
        }
    }

    public long getProcessedCount() {
        return processedCount.get();
    }

    public long getFailedCount() {
        return failedCount.get();
    }
}
