package com.app.aspire.customerloan.entities.enums;

public enum RepaymentStatus {
    PENDING("pending"),
    PAID("paid");

    private String status;

    RepaymentStatus(String status) {
        this.status = status;
    }
    
}
