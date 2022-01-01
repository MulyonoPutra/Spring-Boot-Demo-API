package com.labs.exceptions;

public class CategoriesNotFoundException extends Exception {
    public CategoriesNotFoundException(String message, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
    
