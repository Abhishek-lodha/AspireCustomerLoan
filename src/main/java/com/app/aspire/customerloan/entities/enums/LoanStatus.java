package com.app.aspire.customerloan.entities.enums;

public enum LoanStatus {
    PENDING("pending"),
    APPROVED("approved"),
    PAID("paid"),
    DEFAULTED("defaulted");

    private String status;

    LoanStatus(String status) {
        this.status = status;
    }

}
