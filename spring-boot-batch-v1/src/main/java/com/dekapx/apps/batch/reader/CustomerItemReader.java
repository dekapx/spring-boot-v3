package com.dekapx.apps.batch.reader;

import com.dekapx.apps.entity.Customer;
import com.dekapx.apps.model.Status;
import com.dekapx.apps.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@StepScope
@Component("customerItemReader")
public class CustomerItemReader implements InitializingBean, ItemReader<Customer> {
    @Autowired
    private CustomerService customerService;

    private List<Customer> customers = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.customers.addAll(customerService.findByStatus(Status.INACTIVE));
    }

    @Override
    public Customer read() {
        log.info("CustomerItemReader.read() ");
        return (!this.customers.isEmpty()) ? this.customers.remove(0) : null;
    }
}
