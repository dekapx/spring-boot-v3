package com.dekapx.apps.service;

import com.dekapx.apps.entity.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {
    @Override
    public Contact findById(final Long id) {
        return null;
    }
}
