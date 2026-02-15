package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Contact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ContactRepositoryITest {
    private final ContactRepository repository;

    @Autowired
    public ContactRepositoryITest(final ContactRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    public void setup() {
        this.repository.save(buildContact());
    }

    private Contact buildContact() {
        return Contact.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@gmail.com")
                .phone("123456789")
                .build();
    }

    @AfterEach
    public void tearDown() {
        this.repository.deleteAll();
    }

    @Test
    public void shouldReturnAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        this.repository.findAll().forEach(contacts::add);
        assertThat(contacts)
                .isNotNull()
                .isNotEmpty()
                .hasAtLeastOneElementOfType(Contact.class)
                .filteredOn(c -> !c.getFirstName().isEmpty());
    }
}
