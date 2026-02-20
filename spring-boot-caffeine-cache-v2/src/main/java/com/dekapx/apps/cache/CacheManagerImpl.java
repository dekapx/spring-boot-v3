package com.dekapx.apps.cache;

import com.dekapx.apps.entity.Contact;
import com.dekapx.apps.repository.ContactRepository;
import com.github.benmanes.caffeine.cache.Cache;
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
    private static final String CACHE_KEY = "ALL_USERS";

    private ContactRepository contactRepository;
    private Cache<String, Map<Long, Contact>> cache;

    @Autowired
    public CacheManagerImpl(ContactRepository contactRepository, Cache<String, Map<Long, Contact>> cache) {
        this.contactRepository = contactRepository;
        this.cache = cache;
    }

    public void invalidateAndReload() {
        log.info("Invalidate and reload the cache...");
        cache.invalidate(CACHE_KEY);
        cache.put(CACHE_KEY, toContactsMap());
    }

    public List<Contact> getContacts() {
        Map<Long, Contact> contactsMap = cache.get(CACHE_KEY, key -> toContactsMap());
        return contactsMap.values()
                .stream()
                .collect(Collectors.toList());
    }

    private Map<Long, Contact> toContactsMap() {
        List<Contact> contacts = this.contactRepository.getContacts();
        Map<Long, Contact> contactsMap = contacts.stream()
                .collect(Collectors.toMap(Contact::getId, Function.identity()));
        return contactsMap;
    }

    public void updateCache() {
        Map<Long, Contact> contactsMap = cache.getIfPresent(CACHE_KEY);
        if (contactsMap != null) {
            Contact contact = contactsMap.get(1L);
            if (contact != null) {
                contact.setCity("Dublin");
            }
            contactsMap.put(1L, contact);
        }
        cache.put(CACHE_KEY, contactsMap);
    }
}
