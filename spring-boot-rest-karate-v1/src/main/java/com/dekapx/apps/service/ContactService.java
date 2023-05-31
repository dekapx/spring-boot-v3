package com.dekapx.apps.service;

import com.dekapx.apps.model.Contact;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ContactService {
    Contact findById(Long id);

    List<Contact> findAll();

    Contact save(Contact contact);

    Contact update(Long id, Contact contact);
}
