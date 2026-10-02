package com.dekapx.apps.service;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;

public interface PaymentProcessor {
    PaymentReceipt processPayment(PaymentRequest paymentRequest);
}
