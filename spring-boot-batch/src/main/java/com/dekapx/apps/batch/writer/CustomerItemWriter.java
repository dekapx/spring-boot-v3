package com.dekapx.apps.batch.writer;

import com.dekapx.apps.entity.Customer;
import com.dekapx.apps.repository.CustomerRepository;
import com.dekapx.apps.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("customerItemWriter")
public class CustomerItemWriter implements ItemWriter<Customer> {
    @Autowired
    private CustomerRepository repository;
    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        log.info("CustomerItemWriter.write() ");
        chunk.getItems().forEach(customer -> repository.save(customer));
    }
}
