package com.dekapx.apps.service;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaymentProcessorTest {
    @Autowired
    private PaymentProcessor paymentProcessor;

    @Test
    public void givenPaymentType_shouldProcessPayment() {
        PaymentRequest paymentRequest = getPaymentRequest();
        PaymentReceipt paymentReceipt = this.paymentProcessor.processPayment(paymentRequest);
        assertThat(paymentReceipt)
                .isNotNull()
                .satisfies(receipt -> {
                    assertThat(receipt.customerId()).isEqualTo(paymentRequest.customerId());
                    assertThat(receipt.amount()).isEqualTo(paymentRequest.amount());
                    assertThat(receipt.transactionId()).isNotNull();
                });
    }

    private PaymentRequest getPaymentRequest() {
        return new PaymentRequest("C1001", "CREDIT_CARD",new BigDecimal("100.00"));
    }
}
