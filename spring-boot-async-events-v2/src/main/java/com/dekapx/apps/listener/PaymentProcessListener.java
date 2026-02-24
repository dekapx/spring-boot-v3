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
public class PaymentProcessListener {
    @Async
    @Order(2)
    @EventListener
    public void processPayment(OrderEvent<OrderModel> event) {
        final OrderModel orderModel = event.getModel();
        try {
            log.info("Processing payment for the order: {}", orderModel.getOrderId());
        } catch (Exception ex) {
            log.error("Failed to process payment: {}", orderModel.getOrderId(), ex);
        }
    }
}
