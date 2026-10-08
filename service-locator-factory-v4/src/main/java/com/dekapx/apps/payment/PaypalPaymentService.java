package com.dekapx.apps.payment;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.dekapx.apps.model.PaymentType.PAYPAL;

@Slf4j
@Component
public class PaypalPaymentService implements PaymentService {
    @Override
    public String getType() {
        return PAYPAL;
    }

    @Override
    public PaymentReceipt makePayment(PaymentRequest request) {
        log.info("Processing Paypal payment for customer: {}, amount: {}", request.customerId(), request.amount());
        return new PaymentReceipt("PP" + UUID.randomUUID(),
                PAYPAL,
                request.customerId(),
                request.amount());
    }
}
