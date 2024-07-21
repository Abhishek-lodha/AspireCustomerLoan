package com.app.aspire.customerloan.exceptions;

public class ResourceNotFoundException extends Exception {

    private String message;

    public ResourceNotFoundException(String message) {
        this.message = message;
    }
}
