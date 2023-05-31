package com.dekapx.apps.repository;

import com.dekapx.apps.model.ContactModel;
import com.dekapx.apps.transformer.ContactModelResultTransformer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<ContactModel> loadFromView() {
        return entityManager.createNativeQuery(buildSqlQuery())
                .unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new ContactModelResultTransformer())
                .getResultList();
    }

    private String buildSqlQuery() {
        return new StringBuilder("SELECT first_name as firstName, ")
                .append("last_name as lastName, email ")
                .append("FROM contacts_view")
                .toString();
    }
}

