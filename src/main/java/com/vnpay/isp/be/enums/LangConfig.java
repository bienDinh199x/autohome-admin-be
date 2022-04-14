package com.vnpay.isp.be.enums;

import lombok.Getter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/30/2020
 **/
@Getter
public enum LangConfig {
    VI(1, "vi"),
    EN(2, "en"),
    NOT_FOUND(0, "");

    private int id;
    private String code;

    LangConfig(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public static LangConfig getLangConfigByType(String code) {
        for (LangConfig langConfig : values()) {
            if (code.equalsIgnoreCase(langConfig.getCode())) {
                return langConfig;
            }
        }
        return LangConfig.NOT_FOUND;
    }
}
