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
                        .city("San Francisco")
                        .build(),
                Contact.builder()
                        .id(2L)
                        .firstName("Brian")
                        .lastName("Adams")
                        .email("Brian.Adams@amazon.com")
                        .phone("1234567890")
                        .city("New York")
                        .build(),
                Contact.builder()
                        .id(3L)
                        .firstName("Wayne")
                        .lastName("Smith")
                        .email("Wayne.Smith@meta.com")
                        .phone("1234567890")
                        .city("Los Angeles")
                        .build());
    }
}
