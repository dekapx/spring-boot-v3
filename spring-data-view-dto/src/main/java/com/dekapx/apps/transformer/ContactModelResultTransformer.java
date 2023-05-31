package com.dekapx.apps.transformer;

import com.dekapx.apps.model.ContactModel;
import org.hibernate.transform.ResultTransformer;

import java.util.List;

public class ContactModelResultTransformer implements ResultTransformer {
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        return ContactModel.builder()
                .firstName(tuple[0].toString())
                .lastName(tuple[1].toString())
                .email(tuple[2].toString())
                .build();
    }

    @Override
    public List transformList(List tuples) {
        return tuples;
    }
}
