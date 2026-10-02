package com.dekapx.apps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single order record as returned by the upstream REST API.
 * Deliberately has no {@code id} field -- that's an internal, database-
 * generated identity. {@code orderNumber} is the natural/business key used
 * to detect whether an incoming record is a new order or an update to an
 * existing one (see {@code OrderItemProcessor}).
 * <p>
 * Field names are mapped to the JSON payload via Jackson; adjust to match
 * the actual upstream API contract.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {

    private String orderNumber;
    private String customerName;
    private String itemName;
    private Integer quantity;
    private BigDecimal totalAmount;
    private String status;
    private LocalDate orderDate;
    private LocalDate estimatedDeliveryDate;
    private String trackingNumber;
    private String carrier;
    private String currentLocation;
    private String deliveryAddress;
    private String cancellationReason;
}
