package com.dekapx.apps.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String id) {
        super("Could not find entity for ID " + id);
    }
}
