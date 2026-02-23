package com.dekapx.apps.listener;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventListener {
    @Async
    @EventListener
    public void handleOrderPlacedEvent(OrderEvent<Order> event) {
        Order order = event.getModel();
        log.info("Received OrderEvent for order: {} & Product {}",
                order.getOrderId(),
                order.getProductName());
    }
}
