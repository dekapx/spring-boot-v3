package com.dekapx.apps.cache;

import com.dekapx.apps.entity.Contact;
import com.dekapx.apps.model.Status;
import com.dekapx.apps.repository.ContactRepository;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private Cache caffeineCache;

    @Autowired
    public CacheManagerImpl(ContactRepository contactRepository, Cache caffeineCache) {
        this.contactRepository = contactRepository;
        this.caffeineCache = caffeineCache;
    }

    @PostConstruct
    public void init() {
        log.info("Initialize the cache...");
        reload();
    }

    public void reload() {
        log.info("Invalidate and reload the cache...");
        List<Contact> contacts = this.contactRepository.getContacts();

        Map<Long, Contact> allContactsMap = contacts.stream()
                .collect(Collectors.toConcurrentMap(Contact::getId, Function.identity()));

        Map<Long, Contact> inactiveContactsMap = contacts.stream()
                .filter(contact -> contact.getStatus().equals(Status.INACTIVE))
                .collect(Collectors.toConcurrentMap(Contact::getId, Function.identity()));

        caffeineCache.invalidateAll();

        caffeineCache.put(CACHE_KEY_ALL, allContactsMap);
        caffeineCache.put(CACHE_KEY_INACTIVE, inactiveContactsMap);
    }

    @Override
    public List<Contact> allContacts() {
        Map<Long, Contact> contacts = (Map<Long, Contact>) caffeineCache.getIfPresent(CACHE_KEY_ALL);
        return contacts.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<Contact> inactiveContacts() {
        Map<Long, Contact> contacts = (Map<Long, Contact>) caffeineCache.getIfPresent(CACHE_KEY_INACTIVE);
        return contacts.values().stream().collect(Collectors.toList());
    }


    private Map<Long, Contact> toContactsMap() {
        List<Contact> contacts = this.contactRepository.getContacts();
        Map<Long, Contact> contactsMap = contacts.stream()
                .collect(Collectors.toMap(Contact::getId, Function.identity()));
        return contactsMap;
    }

    public void update(Contact contact) {
        // TODO: Implement update logic
    }
}
