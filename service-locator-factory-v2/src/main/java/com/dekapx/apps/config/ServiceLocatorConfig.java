package com.dekapx.apps.config;

import com.dekapx.apps.clearstream.factory.ClearstreamApiHandlerFactory;
import com.dekapx.apps.euroclear.factory.EuroclearApiHandlerFactory;
import com.dekapx.apps.icsd.factory.IcsdHandlerFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceLocatorConfig {
    @Bean
    public ServiceLocatorFactoryBean icsdHandlerFactoryBean() {
        return getServiceLocatorFactoryBean(IcsdHandlerFactory.class);
    }

    @Bean
    public ServiceLocatorFactoryBean euroclearApiHandlerFactoryBean() {
        return getServiceLocatorFactoryBean(EuroclearApiHandlerFactory.class);
    }

    @Bean
    public ServiceLocatorFactoryBean clearstreamApiHandlerFactoryBean() {
        return getServiceLocatorFactoryBean(ClearstreamApiHandlerFactory.class);
    }

    private ServiceLocatorFactoryBean getServiceLocatorFactoryBean(Class clazz) {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(clazz);
        return factoryBean;
    }
}
