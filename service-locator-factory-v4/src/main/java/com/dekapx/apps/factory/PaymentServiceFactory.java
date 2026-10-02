package com.dekapx.apps.factory;

import com.dekapx.apps.payment.PaymentService;

public interface PaymentServiceFactory {
    PaymentService getPaymentService(String paymentType);
}
