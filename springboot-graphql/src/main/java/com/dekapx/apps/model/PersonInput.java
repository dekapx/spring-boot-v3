package com.dekapx.apps.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class PersonInput {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
