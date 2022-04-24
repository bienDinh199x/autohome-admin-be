package com.autohome.be.enums;

import lombok.Getter;

@Getter
public enum JwtErrorCode {
    SUCCESS("00", "Successfully"),
    VALID("00", "Jwt is valid"),
    INVALID("02", "Jwt is invalid"),
    EXPIRED("03", "Jwt is expired"),
    INVALID_REQUEST("95", "Invalid request"),
    EXCEPTION("99", "Internal error");

    private final String code;
    private final String message;

    JwtErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static JwtErrorCode getJwtErrorCode(String code) {
        for (JwtErrorCode error : values()) {
            if (error.getCode().equals(code)) {
                return error;
            }
        }
        return EXCEPTION;
    }
}
