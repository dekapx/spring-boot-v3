package com.dekapx.apps.config;

import com.dekapx.apps.exception.UnsupportedPaymentTypeException;
import com.dekapx.apps.factory.PaymentServiceFactory;
import com.dekapx.apps.payment.ApplePayPaymentService;
import com.dekapx.apps.payment.CreditCardPaymentService;
import com.dekapx.apps.payment.DebitCardPaymentService;
import com.dekapx.apps.payment.GooglePayPaymentService;
import com.dekapx.apps.payment.PaymentService;
import com.dekapx.apps.payment.PaypalPaymentService;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.dekapx.apps.model.PaymentType.APPLE_PAY;
import static com.dekapx.apps.model.PaymentType.CREDIT_CARD;
import static com.dekapx.apps.model.PaymentType.DEBIT_CARD;
import static com.dekapx.apps.model.PaymentType.GOOGLE_PAY;
import static com.dekapx.apps.model.PaymentType.PAYPAL;
import static com.dekapx.apps.util.BeanUtils.generateBeanName;

@Configuration
public class ServiceLocatorConfig {

    @Bean
    public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(PaymentServiceFactory.class);
        factoryBean.setServiceLocatorExceptionClass(UnsupportedPaymentTypeException.class);
        factoryBean.setServiceMappings(paymentServiceMappings());
        return factoryBean;
    }

    private Properties paymentServiceMappings() {
        Properties mappings = new Properties();
        mappings.put(CREDIT_CARD, getFactoryBeanName(CreditCardPaymentService.class));
        mappings.put(DEBIT_CARD, getFactoryBeanName(DebitCardPaymentService.class));
        mappings.put(PAYPAL, getFactoryBeanName(PaypalPaymentService.class));
        mappings.put(APPLE_PAY, getFactoryBeanName(ApplePayPaymentService.class));
        mappings.put(GOOGLE_PAY, getFactoryBeanName(GooglePayPaymentService.class));
        return mappings;
    }

    public String getFactoryBeanName(Class<? extends PaymentService> clazz) {
        return generateBeanName(clazz);
    }
}
