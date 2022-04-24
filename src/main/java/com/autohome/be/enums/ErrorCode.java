package com.autohome.be.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS("00", "Successfully", "successful"),
    INVALID_DATA("01", "Invalid data", "invalid_data"),
    ALREADY_UPDATED("03", "Already updated", "already_updated"),
    ISP_TXN_NOT_FOUND("04", "Isp Txn not found", "isp_txn_notfound"),
    ISP_TXN_EXPIRED("15", "Isp Txn expired", "tnx_expired"),
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
