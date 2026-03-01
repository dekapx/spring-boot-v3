package com.dekapx.apps.cache;

import com.dekapx.apps.entity.Contact;

import java.util.List;

public interface CacheManager {
    void reload();
    void update(Contact contact);

    List<Contact> allContacts();
    List<Contact> inactiveContacts();
}
