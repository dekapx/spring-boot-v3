package com.dekapx.apps.service;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.Order;
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
    public void placeOrder(Order order) {
        log.info("Placing order {}", order);
        this.eventPublisher.publishEvent(new OrderEvent(this, order));
    }
}
