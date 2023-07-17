package com.dekapx.apps.repository;

import com.dekapx.apps.model.Contact;

import java.util.List;

public interface ContactRepository {
    Contact findById(Long id);

    List<Contact> findAll();

    Contact save(Contact contact);

    Contact update(Long id, Contact contact);
}
