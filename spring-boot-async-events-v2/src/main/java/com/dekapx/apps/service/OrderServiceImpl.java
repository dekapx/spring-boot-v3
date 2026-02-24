package com.dekapx.apps.service;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.OrderModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void placeOrder(OrderModel orderModel) {
        log.info("Placing order {}", orderModel.getOrderId());
        this.eventPublisher.publishEvent(new OrderEvent(this, orderModel));
    }
}
