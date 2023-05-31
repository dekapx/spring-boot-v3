package com.dekapx.apps.repository;

import com.dekapx.apps.model.ContactModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ContactRepositoryITest {
    @Autowired
    private ContactRepository repository;

    @Test
    public void loadFromView() {
        List<ContactModel> contacts = this.repository.loadFromView();
        assertThat(contacts)
                .isNotNull()
                .isNotEmpty()
                .hasAtLeastOneElementOfType(ContactModel.class);
        contacts.forEach(c -> System.out.println(c.getFirstName() + " " + c.getLastName() + " " + c.getEmail()));
    }
}
