package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContactRepositoryITest {
    @Autowired
    private ContactRepository repository;

    @Test
    public void findById() {
        Contact contact = this.repository.findById(1L).get();
        assertThat(contact).isNotNull()
                .satisfies(c -> {
                    assertThat(c.getFirstName()).isEqualTo("Kapil");
                    assertThat(c.getLastName()).isEqualTo("Kumar");
                    assertThat(c.getEmail()).isEqualTo("kapil.kumar@optum.com");
                });
    }

    @Test
    public void findAll() {
        List<Contact> contacts = this.repository.findAll();
        assertThat(contacts)
                .isNotNull()
                .isNotEmpty()
                .hasSize(4)
                .hasAtLeastOneElementOfType(Contact.class);
    }
}
