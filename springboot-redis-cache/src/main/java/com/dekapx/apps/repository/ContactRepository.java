package com.dekapx.apps.repository;

import com.dekapx.apps.entity.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Long> {
}
