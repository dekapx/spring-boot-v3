package com.dekapx.apps.service;

import com.dekapx.apps.entity.Customer;
import com.dekapx.apps.model.Status;

import java.util.List;

public interface CustomerService {
    List<Customer> findByStatus(Status status);
}
