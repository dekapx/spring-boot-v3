package com.dekapx.apps.service;

import com.dekapx.apps.entity.Customer;
import com.dekapx.apps.model.Status;
import com.dekapx.apps.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    @Override
    public List<Customer> findByStatus(Status status) {
        log.info("Find Customer by status [{}]", status);
        return this.repository.findByStatus(status);
    }
}
