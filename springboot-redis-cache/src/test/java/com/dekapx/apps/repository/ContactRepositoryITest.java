package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ContactRepositoryITest {
    private final ContactRepository repository;

    @Autowired
    public ContactRepositoryITest(final ContactRepository repository) {
        this.repository = repository;
    }

    @Test
    public void shouldReturnAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        this.repository.findAll().forEach(contacts::add);
        assertThat(contacts)
                .isNotNull()
                .isNotEmpty()
                .hasAtLeastOneElementOfType(Contact.class);
        ;
    }
}
