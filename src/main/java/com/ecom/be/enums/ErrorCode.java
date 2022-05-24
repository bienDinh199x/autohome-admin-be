package com.ecom.be.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS("00", "Successfully", "successful"),
    INVALID_DATA("01", "Invalid data", "invalid_data"),
    ALREADY_UPDATED("03", "Already updated", "already_updated"),
    INVALID_TOKEN("04", "Token is not found", "invalid_token"),
    ISP_TXN_NOT_FOUND("05", "Txn is not found", "isp_txn_notfound"),
    TOKEN_EXPIRED("15", "Token is expired", "token_expired"),
    INVALID_REQUEST("95", "Invalid request", "invalid_request"),
    EXCEPTION("99", "Internal error", "internal_error");

    private final String code;
    private final String message;
    private final String alias;

    ErrorCode(String code, String message, String alias) {
        this.code = code;
        this.message = message;
        this.alias = alias;
    }
}
