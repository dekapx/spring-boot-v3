package com.dekapx.apps.service;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import com.dekapx.apps.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor{
    private final PaymentService paymentService;

    @Override
    public PaymentReceipt processPayment(PaymentRequest paymentRequest) {
        return this.paymentService.makePayment(paymentRequest);
    }
}
