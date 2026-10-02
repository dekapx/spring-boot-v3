package com.dekapx.apps.exception;

public class UnsupportedPaymentTypeException extends RuntimeException {
    public UnsupportedPaymentTypeException(String message) {
        super(message);
    }

    public UnsupportedPaymentTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
