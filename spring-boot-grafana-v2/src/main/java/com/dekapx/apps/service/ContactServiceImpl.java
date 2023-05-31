package com.dekapx.apps.service;

import com.dekapx.apps.entity.Contact;
import com.dekapx.apps.exception.NotFoundException;
import com.dekapx.apps.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    private ContactRepository repository;

    @Autowired
    public ContactServiceImpl(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contact findById(final Long id) {
        final Optional<Contact> contactOptional = this.repository.findById(id);
        log.info("Perform lookup for contactID [{}]", id);
        return contactOptional.orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
