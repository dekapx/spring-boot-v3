package com.dekapx.apps.model;

import java.math.BigDecimal;

public record PaymentReceipt(
        String transactionId,
        String paymentType,
        String customerId,
        BigDecimal amount) {
}
