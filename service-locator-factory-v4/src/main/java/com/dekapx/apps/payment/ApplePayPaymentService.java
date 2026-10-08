package com.dekapx.apps.payment;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.dekapx.apps.model.PaymentType.CREDIT_CARD;

@Slf4j
@Component
public class ApplePayPaymentService implements PaymentService {
    @Override
    public String getType() {
        return "APPLE_PAY";
    }

    @Override
    public PaymentReceipt makePayment(PaymentRequest request) {
        log.info("Processing apple pay payment for customer: {}, amount: {}", request.customerId(), request.amount());
        return new PaymentReceipt("AP" + UUID.randomUUID(),
                "APPLE_PAY",
                request.customerId(),
                request.amount());
    }
}
