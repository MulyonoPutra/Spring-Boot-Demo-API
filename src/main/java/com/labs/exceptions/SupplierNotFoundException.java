package com.labs.exceptions;

public class SupplierNotFoundException extends Exception {
    public SupplierNotFoundException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
