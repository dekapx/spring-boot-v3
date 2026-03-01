package com.dekapx.apps.service;

import com.dekapx.apps.cache.CacheManager;
import com.dekapx.apps.entity.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    private CacheManager cacheManager;

    @Autowired
    public ContactServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<Contact> getAllContacts() {
        return this.cacheManager.allContacts();
    }

    @Override
    public List<Contact> getInactiveContacts() {
        return this.cacheManager.inactiveContacts();
    }

    @Override
    public void update() {

    }
}
