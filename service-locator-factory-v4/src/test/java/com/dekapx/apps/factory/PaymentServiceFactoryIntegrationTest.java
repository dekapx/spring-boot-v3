package com.dekapx.apps.factory;

import com.dekapx.apps.exception.UnsupportedPaymentTypeException;
import com.dekapx.apps.payment.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class PaymentServiceFactoryIntegrationTest {
    @Autowired
    private PaymentServiceFactory paymentServiceFactory;

    @ParameterizedTest(name = "PaymentService {0} to {1}")
    @CsvSource({
            "CREDIT_CARD, CreditCardPaymentService",
            "DEBIT_CARD, DebitCardPaymentService"
    })
    public void givenPaymentType_whenGetPaymentService_thenReturnsExpectedService(String paymentType, String expectedBeanName) {
        PaymentService paymentService = paymentServiceFactory.getPaymentService(paymentType);
        assertThat(paymentService)
                .isNotNull()
                .satisfies(service -> {
                    assertThat(service).isInstanceOf(PaymentService.class);
                    assertThat(service.getClass().getSimpleName()).isEqualTo(expectedBeanName);
                });
    }


    @Test
    public void givenInvalidPaymentType_whenGetPaymentService_thenThrowsException() {
        assertThatThrownBy(() -> paymentServiceFactory.getPaymentService("INVALID_PAYMENT_TYPE"))
                .isInstanceOf(UnsupportedPaymentTypeException.class);
    }
}
