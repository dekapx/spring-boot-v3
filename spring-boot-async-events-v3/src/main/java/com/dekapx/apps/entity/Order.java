package com.dekapx.apps.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Order {
    private Long orderId;
    private String productName;
    private int quantity;
    private double price;
}
