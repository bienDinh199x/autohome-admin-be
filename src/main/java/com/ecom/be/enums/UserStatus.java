package com.ecom.be.enums;

public enum UserStatus {
    ACTIVE(1, "active"),
    LOCK(0, "lock");

    private int statusCode;
    private String statusDesc;

    UserStatus(int code, String desc) {
        this.statusCode = code;
        this.statusDesc = desc;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }
}
