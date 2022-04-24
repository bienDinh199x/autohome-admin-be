package com.autohome.be.enums;

import lombok.Getter;

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
