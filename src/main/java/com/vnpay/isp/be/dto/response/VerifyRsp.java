package com.vnpay.isp.be.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/16/2020
 **/
@Getter
@Setter
public class VerifyRsp {
    private String notifyUrl;
    private String data;
}
