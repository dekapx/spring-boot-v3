package com.dekapx.apps.service;

import com.dekapx.apps.cache.CacheManagerImpl;
import com.dekapx.apps.entity.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    private CacheManagerImpl cacheManagerImpl;

    @Autowired
    public ContactServiceImpl(CacheManagerImpl cacheManagerImpl) {
        this.cacheManagerImpl = cacheManagerImpl;
    }

    @Override
    public List<Contact> getContacts() {
        log.info("Fetch contacts from service....");
        return this.cacheManagerImpl.getContacts();
    }

    @Override
    public void invalidateAndReload() {
        this.cacheManagerImpl.invalidateAndReload();
    }

    @Override
    public void updateCache() {
        this.cacheManagerImpl.updateCache();
    }
}
