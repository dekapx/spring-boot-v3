package com.dekapx.apps.exception;

/**
 * Thrown when an incoming order record fails validation. Registered as a
 * skippable exception on the step so a handful of bad records do not abort
 * the entire ingestion job.
 */
public class OrderProcessingException extends RuntimeException {

    public OrderProcessingException(String message) {
        super(message);
    }

    public OrderProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
