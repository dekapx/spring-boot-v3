package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Customer;
import com.dekapx.apps.model.Status;

public class CustomerTestData {
    public static final String FIRST_NAME = "Test";
    public static final String LAST_NAME = "User";
    public static final String PHONE = "1 123 456 7890";
    public static final String EMAIL = "test.user@google.com";

    public static Customer createCustomer() {
        return Customer.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .email(EMAIL)
                .status(Status.ACTIVE)
                .build();
    }
}
