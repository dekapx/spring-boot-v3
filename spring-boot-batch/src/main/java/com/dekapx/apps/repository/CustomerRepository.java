package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Customer;
import com.dekapx.apps.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByFirstNameAndLastName(String firstName, String lastName);

    List<Customer> findByStatus(Status status);
}