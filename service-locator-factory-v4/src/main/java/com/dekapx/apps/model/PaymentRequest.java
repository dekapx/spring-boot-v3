package com.dekapx.apps.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record PaymentRequest (
        @NotBlank String customerId,
        @NotBlank String paymentType,
        @NotBlank @DecimalMin(value = "0.01") BigDecimal amount) {
}
