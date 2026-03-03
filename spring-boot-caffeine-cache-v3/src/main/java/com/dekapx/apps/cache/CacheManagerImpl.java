package com.dekapx.apps.cache;

import com.dekapx.apps.entity.Contact;
import com.dekapx.apps.model.Status;
import com.dekapx.apps.repository.ContactRepository;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CacheManagerImpl implements CacheManager {
    private static final String CACHE_KEY_ALL = "ALL";
    private static final String CACHE_KEY_INACTIVE = "INACTIVE";

    private ContactRepository contactRepository;
    private Cache<String, Map<Long, Contact>> caffeineCache;

    @Autowired
    public CacheManagerImpl(ContactRepository contactRepository, Cache<String, Map<Long, Contact>> caffeineCache) {
        this.contactRepository = contactRepository;
        this.caffeineCache = caffeineCache;
    }

    public void reload() {
        log.info("Invalidate and reload the cache...");
        List<Contact> contacts = this.contactRepository.getContacts();

        Map<Long, Contact> allContactsMap = contacts.stream()
                .collect(Collectors.toConcurrentMap(Contact::getId, Function.identity()));

        Map<Long, Contact> inactiveContactsMap = contacts.stream()
                .filter(contact -> contact.getStatus().equals(Status.INACTIVE))
                .collect(Collectors.toConcurrentMap(Contact::getId, Function.identity()));

        caffeineCache.put(CACHE_KEY_ALL, allContactsMap);
        caffeineCache.put(CACHE_KEY_INACTIVE, inactiveContactsMap);
    }

    @Override
    public List<Contact> allContacts() {
        Map<Long, Contact> contacts = caffeineCache.getIfPresent(CACHE_KEY_ALL);
        return contacts == null ? Collections.emptyList() : new ArrayList<>(contacts.values());
    }

    @Override
    public List<Contact> inactiveContacts() {
        Map<Long, Contact> contacts = caffeineCache.getIfPresent(CACHE_KEY_INACTIVE);
        return contacts == null ? Collections.emptyList() : new ArrayList<>(contacts.values());
    }

    public void update(Contact contact) {
        Map<Long, Contact> allContacts = caffeineCache.getIfPresent(CACHE_KEY_ALL);
        if (allContacts == null) {
            log.warn("No cache found for contact: {}", contact);
        }
        allContacts.put(contact.getId(), contact);
        log.info("Updated cache for contact: {}", contact);

        Map<Long, Contact> inactiveContacts = caffeineCache.getIfPresent(CACHE_KEY_INACTIVE);
        if (contact.getStatus().equals(Status.INACTIVE)) {
            if (inactiveContacts == null) {
                log.warn("No cache found for inactive contact: {}", contact);
            } else {
                inactiveContacts.put(contact.getId(), contact);
                log.info("Updated cache for inactive contact: {}", contact);
            }
        }
    }
}
