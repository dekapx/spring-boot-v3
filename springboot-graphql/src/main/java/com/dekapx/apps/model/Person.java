package com.dekapx.apps.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Person {

    @Id
    private Long id;
    private String name;
}
