package com.dekapx.apps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Order {
    private String orderId;
    private String productName;
    private int quantity;
    private double price;
}
