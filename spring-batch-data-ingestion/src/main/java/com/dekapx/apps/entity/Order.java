package com.dekapx.apps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Target persistence entity for an ingested order record.
 * <p>
 * Uses {@code GenerationType.SEQUENCE} rather than {@code IDENTITY}:
 * Hibernate cannot batch JDBC inserts when IDENTITY is used (it needs the
 * generated key back from each individual insert before it can continue),
 * which would silently defeat the {@code hibernate.jdbc.batch_size} setting
 * configured for this app. A sequence with an allocation size matching the
 * batch size lets Hibernate pre-allocate IDs in memory and batch the inserts.
 */
@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(name = "uk_orders_order_number", columnNames = "order_number"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_id_seq", allocationSize = 200)
    private Long id;

    @Column(name = "order_number", nullable = false, length = 64)
    private String orderNumber;

    @Column(name = "customer_name", nullable = false, length = 255)
    private String customerName;

    @Column(name = "item_name", nullable = false, length = 255)
    private String itemName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "estimated_delivery_date")
    private LocalDate estimatedDeliveryDate;

    @Column(name = "tracking_number", length = 64)
    private String trackingNumber;

    @Column(name = "carrier", length = 64)
    private String carrier;

    @Column(name = "current_location", length = 255)
    private String currentLocation;

    @Column(name = "delivery_address", length = 512)
    private String deliveryAddress;

    @Column(name = "cancellation_reason", length = 512)
    private String cancellationReason;
}
