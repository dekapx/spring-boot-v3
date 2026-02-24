package com.dekapx.apps.listener;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderShippingListener {
    @Async
    @Order(3)
    @EventListener
    public void shipOrder(OrderEvent<OrderModel> event) {
        final OrderModel orderModel = event.getModel();
        try {
            log.info("Shipping Order: {}", orderModel.getOrderId());
        } catch (Exception ex) {
            log.error("Failed to ship Order: {}", orderModel.getOrderId(), ex);
        }
    }
}
