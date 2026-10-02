package com.dekapx.apps.payment;

import com.dekapx.apps.model.PaymentReceipt;
import com.dekapx.apps.model.PaymentRequest;
import com.dekapx.apps.model.PaymentType;

public interface PaymentService {
    String getType();

    PaymentReceipt makePayment(PaymentRequest paymentRequest);
}
