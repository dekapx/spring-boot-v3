package com.dekapx.apps.cache;

import com.dekapx.apps.entity.Contact;
import com.dekapx.apps.repository.ContactRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ContactCache {
    private static final String CACHE_KEY = "ALL_USERS";

    private ContactRepository contactRepository;
    private Cache<String, List<Contact>> contactCache;

    @Autowired
    public ContactCache(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
        this.contactCache = Caffeine.newBuilder()
                .initialCapacity(5)
                .maximumSize(10)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .recordStats()
                .build();
    }

    public List<Contact> getContacts() {
        return contactCache.get(CACHE_KEY, key -> this.contactRepository.getContacts());
    }
}
