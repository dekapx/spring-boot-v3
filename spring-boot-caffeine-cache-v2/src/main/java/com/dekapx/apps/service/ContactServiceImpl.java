package com.dekapx.apps.service;

import com.dekapx.apps.cache.ContactCache;
import com.dekapx.apps.entity.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    private ContactCache contactCache;

    @Autowired
    public ContactServiceImpl(ContactCache contactCache) {
        this.contactCache = contactCache;
    }

    @Override
    public List<Contact> getContacts() {
        log.info("Fetch contacts from service....");
        return this.contactCache.getContacts();
    }

    @Override
    public void invalidateAndReload() {
        this.contactCache.invalidateAndReload();
    }

    @Override
    public void updateCache() {
        this.contactCache.updateCache();
    }
}
