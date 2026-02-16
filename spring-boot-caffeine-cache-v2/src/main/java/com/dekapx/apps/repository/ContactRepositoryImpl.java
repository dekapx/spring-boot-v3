package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ContactRepositoryImpl implements ContactRepository {
    @Override
    public List<Contact> getContacts() {
        log.info("---------------- Fetch contacts from DB ----------------");
        return List.of(Contact.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@outlook.com")
                        .phone("1234567890")
                        .build(),
                Contact.builder()
                        .id(2L)
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("Jane.Doe@amazon.com")
                        .phone("0987654321")
                        .build());
    }
}
