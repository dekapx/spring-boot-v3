package com.dekapx.apps.listener;

import com.dekapx.apps.dto.OrderDto;
import com.dekapx.apps.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

/**
 * Captures records that were skipped (rather than silently dropped) so they
 * can be logged, written to a dead-letter table/topic, or alerted on. In
 * this sample they are logged at WARN level.
 */
@Slf4j
@Component
public class OrderSkipListener implements SkipListener<OrderDto, Order> {

    @Override
    public void onSkipInRead(Throwable t) {
        log.warn("Skipped an order during READ: {}", t.getMessage());
    }

    @Override
    public void onSkipInProcess(OrderDto item, Throwable t) {
        log.warn("Skipped order {} during PROCESS: {}", item, t.getMessage());
    }

    @Override
    public void onSkipInWrite(Order item, Throwable t) {
        log.warn("Skipped order {} during WRITE: {}", item, t.getMessage());
    }
}
