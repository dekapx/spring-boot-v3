package com.dekapx.apps.payment;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.dekapx.apps.model.PaymentType.DEBIT_CARD;

@Slf4j
@Component("debitCardPaymentService")
public class DebitCardPaymentService implements PaymentService {
    @Override
    public String getType() {
        return DEBIT_CARD;
    }

    @Override
    public PaymentReceipt makePayment(PaymentRequest request) {
        log.info("Processing debit card payment for customer: {}, amount: {}", request.customerId(), request.amount());
        return new PaymentReceipt("CC" + UUID.randomUUID(),
                DEBIT_CARD,
                request.customerId(),
                request.amount());
    }
}
