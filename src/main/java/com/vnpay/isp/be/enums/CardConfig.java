package com.vnpay.isp.be.enums;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/18/2020
 **/
@Getter
public enum CardConfig {
    VISA("VISA", "001", "^4[0-9]*"),
    MASTERCARD("MASTERCARD", "002", "^(2(22[1-9]|[3-6]|7[0-1]|720)|5|60(0|1[0|[2-9]]|[2-9])|6([1-3]|[6-9]))[0-9]*"),
    AMEX("AMEX", "003", "^(34|37)[0-9]*"),
    JCB("JCB", "007", "^35(2[89]|[3-8][0-9])[0-9]*"),
    NOT_FOUND("NOT_FOUND", "", "");

    private final String scheme;
    private final String type;
    private final String regex;

    CardConfig(String scheme, String type, String regex) {
        this.scheme = scheme;
        this.type = type;
        this.regex = regex;
    }

    public static CardConfig getCardConfigByScheme(String scheme) {
        for (CardConfig cardConfig : values()) {
            if (scheme.equals(cardConfig.getScheme())) {
                return cardConfig;
            }
        }
        return CardConfig.NOT_FOUND;
    }

    public static CardConfig getCardConfigByCardNumber(String cardNumber) {
        for (CardConfig cardConfig : values()) {
            if (Pattern.matches(cardConfig.getRegex(), cardNumber)) {
                return cardConfig;
            }
        }
        return CardConfig.NOT_FOUND;
    }
}
