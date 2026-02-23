package com.dekapx.apps.service;

import com.dekapx.apps.listener.TestApplicationContextAware;
import com.dekapx.apps.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private TestApplicationContextAware applicationContextAware;

    @Test
    public void placeBulkOrders() {
        IntStream.range(0, 10).forEach(count -> {
            this.orderService.placeOrder(Order.builder()
                    .orderId("ORD-12345-" + count)
                    .productName("Laptop")
                    .quantity(10)
                    .price(1000.00)
                    .build());
        });
    }
}
