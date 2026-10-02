package com.dekapx.apps.processor;

import com.dekapx.apps.dto.OrderDto;
import com.dekapx.apps.entity.Order;
import com.dekapx.apps.exception.OrderProcessingException;
import com.dekapx.apps.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Validates each incoming {@link OrderDto} and maps it onto an {@link Order}
 * entity ready for the JPA writer.
 * <p>
 * Upsert behavior: {@code orderNumber} is treated as the natural/business
 * key. If an order with the same {@code orderNumber} already exists, this
 * processor loads it and overwrites its fields (so the JPA writer performs
 * an UPDATE); otherwise it builds a new, transient {@code Order} (so the
 * writer performs an INSERT). This makes the ingestion job idempotent --
 * re-running it (or resuming after a partial failure) does not create
 * duplicate rows.
 * <p>
 * Note: this issues one {@code findByOrderNumber} lookup per record. For
 * very high-throughput ingestion where most records are brand-new inserts
 * rather than updates, consider batching this lookup (e.g. an
 * {@code ItemReadListener}/{@code ItemProcessListener} that pre-fetches
 * existing order numbers for the whole chunk in a single query) -- see the
 * README's "Performance notes" section.
 */
@Slf4j
@Component
public class OrderItemProcessor implements ItemProcessor<OrderDto, Order> {

    private final OrderRepository orderRepository;

    public OrderItemProcessor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order process(OrderDto dto) {
        validate(dto);

        Order order = orderRepository.findByOrderNumber(dto.getOrderNumber())
                .orElseGet(Order::new);

        order.setOrderNumber(dto.getOrderNumber());
        order.setCustomerName(dto.getCustomerName());
        order.setItemName(dto.getItemName());
        order.setQuantity(dto.getQuantity());
        order.setTotalAmount(dto.getTotalAmount());
        order.setStatus(dto.getStatus());
        order.setOrderDate(dto.getOrderDate());
        order.setEstimatedDeliveryDate(dto.getEstimatedDeliveryDate());
        order.setTrackingNumber(dto.getTrackingNumber());
        order.setCarrier(dto.getCarrier());
        order.setCurrentLocation(dto.getCurrentLocation());
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setCancellationReason(dto.getCancellationReason());

        return order;
    }

    private void validate(OrderDto dto) {
        if (dto.getOrderNumber() == null || dto.getOrderNumber().isBlank()) {
            throw new OrderProcessingException("Order missing required orderNumber: " + dto);
        }
        if (dto.getCustomerName() == null || dto.getCustomerName().isBlank()) {
            throw new OrderProcessingException("Order " + dto.getOrderNumber() + " missing required customerName");
        }
        if (dto.getItemName() == null || dto.getItemName().isBlank()) {
            throw new OrderProcessingException("Order " + dto.getOrderNumber() + " missing required itemName");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new OrderProcessingException("Order " + dto.getOrderNumber() + " has invalid quantity: " + dto.getQuantity());
        }
        if (dto.getTotalAmount() == null || dto.getTotalAmount().signum() < 0) {
            throw new OrderProcessingException("Order " + dto.getOrderNumber() + " has invalid totalAmount: " + dto.getTotalAmount());
        }
        if (dto.getOrderDate() == null) {
            throw new OrderProcessingException("Order " + dto.getOrderNumber() + " missing required orderDate");
        }
    }
}
