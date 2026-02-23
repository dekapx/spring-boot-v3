package com.dekapx.apps.listener;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Supplier;

@Slf4j
@SpringBootTest
public class OrderEventListenerTest {
    @Autowired
    private TestApplicationContextAware applicationContextAware;

    @Test
    public void publishUserOnboardingEvent() {
        OrderEvent<Order> orderEvent = prepareOrderEvent(orderSupplier.get());
        log.info("Publishing UserOnboardingEvent...");

        this.applicationContextAware.getApplicationContext().publishEvent(orderEvent);
        log.info("UserOnboardingEvent published...");
    }

    private OrderEvent<Order> prepareOrderEvent(Order order) {
        return new OrderEvent(this, order);
    }

    private Supplier<Order> orderSupplier = () ->
       Order.builder()
               .orderId("ORD-12345")
               .productName("Laptop")
               .quantity(10)
               .price(1000.00)
               .build();
}
