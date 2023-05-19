package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ContactRepositoryITest {
    @Autowired
    private ContactRepository repository;

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
