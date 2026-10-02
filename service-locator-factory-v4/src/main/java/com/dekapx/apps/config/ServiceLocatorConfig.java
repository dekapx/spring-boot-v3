package com.dekapx.apps.config;

import com.dekapx.apps.exception.UnsupportedPaymentTypeException;
import com.dekapx.apps.factory.PaymentServiceFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.dekapx.apps.model.PaymentType.CREDIT_CARD;

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
        mappings.put(CREDIT_CARD, "creditCardPaymentService");
        return mappings;
    }


}
