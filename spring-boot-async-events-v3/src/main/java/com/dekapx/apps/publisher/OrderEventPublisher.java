package com.dekapx.apps.publisher;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.OrderModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void placeOrder(OrderModel orderModel) {
        log.info("Placing order {}", orderModel.getOrderId());
        this.eventPublisher.publishEvent(new OrderEvent(this, orderModel));
    }
}
