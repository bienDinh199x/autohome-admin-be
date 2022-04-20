package com.autohome.be.enums;

public enum UserStatus {
    ACTIVE(0, "active"),
    LOCK(1, "lock");

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
