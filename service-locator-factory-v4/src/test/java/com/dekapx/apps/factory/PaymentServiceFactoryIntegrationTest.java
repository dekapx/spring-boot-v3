package com.dekapx.apps.factory;

import com.dekapx.apps.exception.UnsupportedPaymentTypeException;
import com.dekapx.apps.payment.ApplePayPaymentService;
import com.dekapx.apps.payment.CreditCardPaymentService;
import com.dekapx.apps.payment.DebitCardPaymentService;
import com.dekapx.apps.payment.GooglePayPaymentService;
import com.dekapx.apps.payment.PaymentService;
import com.dekapx.apps.payment.PaypalPaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static com.dekapx.apps.model.PaymentType.APPLE_PAY;
import static com.dekapx.apps.model.PaymentType.CREDIT_CARD;
import static com.dekapx.apps.model.PaymentType.DEBIT_CARD;
import static com.dekapx.apps.model.PaymentType.GOOGLE_PAY;
import static com.dekapx.apps.model.PaymentType.PAYPAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class PaymentServiceFactoryIntegrationTest {
    @Autowired
    private PaymentServiceFactory paymentServiceFactory;

    @ParameterizedTest
    @MethodSource("paymentServices")
    void givenPaymentType_whenGetPaymentService_thenReturnsExpectedService(
            String paymentType, Class<? extends PaymentService> expectedClass) {
        assertThat(paymentServiceFactory.getPaymentService(paymentType))
                .isExactlyInstanceOf(expectedClass);
    }

    static Stream<Arguments> paymentServices() {
        return Stream.of(
                Arguments.of(CREDIT_CARD, CreditCardPaymentService.class),
                Arguments.of(DEBIT_CARD, DebitCardPaymentService.class),
                Arguments.of(PAYPAL, PaypalPaymentService.class),
                Arguments.of(APPLE_PAY, ApplePayPaymentService.class),
                Arguments.of(GOOGLE_PAY, GooglePayPaymentService.class)
        );
    }


    @Test
    public void givenInvalidPaymentType_whenGetPaymentService_thenThrowsException() {
        assertThatThrownBy(() -> paymentServiceFactory.getPaymentService("INVALID_PAYMENT_TYPE"))
                .isInstanceOf(UnsupportedPaymentTypeException.class);
    }
}
