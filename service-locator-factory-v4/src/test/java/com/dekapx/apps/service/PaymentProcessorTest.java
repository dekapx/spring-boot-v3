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
    void givenPaymentRequest_whenProcessing_thenReturnsReceiptWithMatchingDetails() {
        PaymentRequest request =
                new PaymentRequest("C1001", "CREDIT_CARD", new BigDecimal("100.00"));

        assertThat(paymentProcessor.processPayment(request))
                .isNotNull()
                .returns(request.customerId(), PaymentReceipt::customerId)
                .returns(request.amount(), PaymentReceipt::amount)
                .matches(receipt -> receipt.transactionId() != null,
                        "transaction ID is generated");
    }

    private PaymentRequest getPaymentRequest() {
        return new PaymentRequest("C1001", "CREDIT_CARD",new BigDecimal("100.00"));
    }
}
