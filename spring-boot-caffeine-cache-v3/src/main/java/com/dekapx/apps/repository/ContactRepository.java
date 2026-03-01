package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Contact;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ContactRepository {
    List<Contact> getContacts();
}
