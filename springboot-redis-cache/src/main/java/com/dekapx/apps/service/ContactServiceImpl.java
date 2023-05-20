package com.dekapx.apps.service;

import com.dekapx.apps.entity.Contact;
import com.dekapx.apps.exception.ResourceNotFoundException;
import com.dekapx.apps.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository repository;

    @Autowired
    public ContactServiceImpl(final ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contact findById(final Long id) {
        log.info("Perform lookup for Contact ID [{}]", id);
        return this.repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Contact with ID [%d] not found.", id)));
    }
}
