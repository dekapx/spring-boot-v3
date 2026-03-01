package com.dekapx.apps.service;

import com.dekapx.apps.entity.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getAllContacts();

    List<Contact> getInactiveContacts();

    void update();
}
