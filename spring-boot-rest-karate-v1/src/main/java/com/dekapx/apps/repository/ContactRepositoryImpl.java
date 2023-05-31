package com.dekapx.apps.repository;

import com.dekapx.apps.model.Contact;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContactRepositoryImpl implements ContactRepository {
    private Map<Long, Contact> entries = new HashMap<>() {{
        put(1L, Contact.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Person")
                .email("test.person@google.com")
                .phone("+353 089 123 4567")
                .build());
    }};

    @Override
    public Contact findById(Long id) {
        return this.entries.get(id);
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        this.entries.entrySet().forEach(entry -> contacts.add(entry.getValue()));
        return contacts;
    }

    @Override
    public Contact save(Contact contact) {
        Long id = Integer.valueOf(this.entries.size() + 1).longValue();
        contact.setId(id);
        this.entries.put(id, contact);
        return contact;
    }

    @Override
    public Contact update(Long id, Contact contact) {
        this.entries.put(id, contact);
        return contact;
    }
}
