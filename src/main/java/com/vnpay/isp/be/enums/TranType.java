package com.vnpay.isp.be.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * @since 10/18/2021
 **/
@Getter
@AllArgsConstructor
public enum TranType {
    ISP("04"),
    RECURRING("05"),
    UNKNOWN("");;

    private String code;

    public static TranType getInstance(String code) {
        for (TranType i : values()) {
            if (i.getCode().equals(code)) {
                return i;
            }
        }
        return UNKNOWN;
    }
}
