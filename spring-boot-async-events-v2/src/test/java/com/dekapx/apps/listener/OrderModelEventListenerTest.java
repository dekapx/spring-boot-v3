package com.dekapx.apps.listener;

import com.dekapx.apps.event.OrderEvent;
import com.dekapx.apps.model.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Supplier;

@Slf4j
@SpringBootTest
public class OrderModelEventListenerTest {
    @Autowired
    private TestApplicationContextAware applicationContextAware;

    @Test
    public void publishUserOnboardingEvent() {
        OrderEvent<OrderModel> orderEvent = prepareOrderEvent(orderSupplier.get());
        log.info("Publishing UserOnboardingEvent...");

        this.applicationContextAware.getApplicationContext().publishEvent(orderEvent);
        log.info("UserOnboardingEvent published...");
    }

    private OrderEvent<OrderModel> prepareOrderEvent(OrderModel orderModel) {
        return new OrderEvent(this, orderModel);
    }

    private Supplier<OrderModel> orderSupplier = () ->
       OrderModel.builder()
               .orderId("ORD-12345")
               .productName("Laptop")
               .quantity(10)
               .price(1000.00)
               .build();
}
