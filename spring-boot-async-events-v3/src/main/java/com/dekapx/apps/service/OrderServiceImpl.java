package com.dekapx.apps.service;

import com.dekapx.apps.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Override
    public void saveOrder(Order order) {
        log.info("Saving order {}", order.getOrderId());
    }
}
