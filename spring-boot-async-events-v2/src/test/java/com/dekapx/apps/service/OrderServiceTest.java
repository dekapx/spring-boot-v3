package com.dekapx.apps.service;

import com.dekapx.apps.listener.OrderEventListener;
import com.dekapx.apps.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderEventListener orderEventListener;

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

        log.info("Order Success Count {}", this.orderEventListener.getProcessedCount());
        log.info("Order Failure Count {}", this.orderEventListener.getFailedCount());
    }
}
