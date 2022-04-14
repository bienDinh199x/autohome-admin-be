package com.vnpay.isp.be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/28/2020
 **/
@Getter
@Setter
@AllArgsConstructor
public class JwtValidateRequest {
    private String accessToken;
    private String jwtToken;
}
