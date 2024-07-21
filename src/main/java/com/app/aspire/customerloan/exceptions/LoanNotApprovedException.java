package com.app.aspire.customerloan.exceptions;

public class LoanNotApprovedException extends Exception {

    private String message;

    public LoanNotApprovedException(String message) {
        this.message = message;
    }

}
