package com.dekapx.apps.controller;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import com.dekapx.apps.payment.PaymentService;
import com.dekapx.apps.service.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentProcessor paymentProcessor;

    @Autowired
    public PaymentController(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentReceipt> makePayment(PaymentRequest paymentRequest) {
        PaymentReceipt receipt = paymentProcessor.processPayment(paymentRequest);
        return ResponseEntity.ok(receipt);
    }
}
