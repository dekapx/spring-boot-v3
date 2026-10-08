package com.dekapx.apps.payment;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.dekapx.apps.model.PaymentType.CREDIT_CARD;

@Slf4j
@Component
public class GooglePayPaymentService implements PaymentService {
    @Override
    public String getType() {
        return "GOOGLE_PAY";
    }

    @Override
    public PaymentReceipt makePayment(PaymentRequest request) {
        log.info("Processing google pay payment for customer: {}, amount: {}", request.customerId(), request.amount());
        return new PaymentReceipt("GP" + UUID.randomUUID(),
                "GOOGLE_PAY",
                request.customerId(),
                request.amount());
    }
}
