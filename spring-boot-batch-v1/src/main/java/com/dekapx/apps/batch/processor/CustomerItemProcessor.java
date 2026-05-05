package com.dekapx.apps.batch.processor;

import com.dekapx.apps.entity.Customer;
import com.dekapx.apps.model.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component("customerItemProcessor")
public class CustomerItemProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) {
        customer.setStatus(Status.ARCHIVED);
        log.info("CustomerItemProcessor.process() ");
        return customer;
    }
}
