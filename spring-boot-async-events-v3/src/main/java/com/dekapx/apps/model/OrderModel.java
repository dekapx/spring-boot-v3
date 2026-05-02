package com.dekapx.apps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderModel {
    private String productName;
    private int quantity;
    private double price;
}
