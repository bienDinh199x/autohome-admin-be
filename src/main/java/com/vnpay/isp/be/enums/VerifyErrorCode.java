package com.vnpay.isp.be.enums;

import lombok.Getter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/11/2020
 **/
@Getter
public enum VerifyErrorCode {
    SUCCESS("00", "Card verify success", "success"),
    ISP_TXN_NOT_FOUND("01", "Isp Txn not found", "isp_txn_notfound"),
    NOT_MATCH_SCHEME("02", "Card number not match scheme", "card_not_match_scheme"),
    INVALID_DATA_FORMAT("03", "Invalid data format", "invalid_data_format"),
    NOT_ACTIVE_ISP("04", "Card not support installment payment", "card_not_support_isp"),
    INVALID_CONDITIONS("05", "Invalid installment payment conditions", "invalid_isp_conditions"),
    CARD_IN_STATEMENT_DATE("07", "Card in statement date","card_in_statement_date"),
    TOO_MANY_TIME("74", "Too many times for process tnx.", "verify_too_many_times"),
    EXCEPTION("99", "Internal error", "internal_error");

    private final String code;
    private final String message;
    private final String alias;

    VerifyErrorCode(String code, String message, String alias) {
        this.code = code;
        this.message = message;
        this.alias = alias;
    }
}
