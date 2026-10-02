package com.dekapx.apps.payment;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import com.dekapx.apps.model.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.dekapx.apps.model.PaymentType.CREDIT_CARD;

@Slf4j
@Component
public class CreditCardPaymentService implements PaymentService {
    @Override
    public String getType() {
        return CREDIT_CARD;
    }

    @Override
    public PaymentReceipt makePayment(PaymentRequest request) {
        log.info("Processing credit card payment for customer: {}, amount: {}", request.customerId(), request.amount());
        return new PaymentReceipt("CC" + UUID.randomUUID(),
                CREDIT_CARD,
                request.customerId(),
                request.amount());
    }
}
