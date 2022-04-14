package com.vnpay.isp.be.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vnpay.isp.be.common.Utils;
import com.vnpay.isp.be.dto.request.JwtValidateRequest;
import com.vnpay.isp.be.dto.response.Response;
import com.vnpay.isp.be.enums.JwtErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/26/2020
 **/
@Slf4j
@Service
public class JwtAuthService extends BaseService {

    public Response<String> validateJwt(String jwtToken, String accessToken) {
        Response<String> response = new Response<>();
        try {
            if (!StringUtils.isEmpty(jwtToken) && !StringUtils.isEmpty(accessToken)) {
                String url = ispAuthUrl + "/payment/validate-token";
                JwtValidateRequest validateRequest = new JwtValidateRequest(accessToken, jwtToken);
                String rsp = restPost(url, Utils.toJson(validateRequest));
                response = Utils.fromJson(rsp, new TypeReference<>() {
                });
            } else {
                response.setRspCode(JwtErrorCode.INVALID_REQUEST.getCode());
                response.setRspMsg(JwtErrorCode.INVALID_REQUEST.getMessage());
            }
        } catch (Exception ex) {
            log.error("Exception validateJwt: jwtToken={} - accessToken={}", jwtToken, accessToken, ex);
            response.setRspCode(JwtErrorCode.EXCEPTION.getCode());
            response.setRspMsg(JwtErrorCode.EXCEPTION.getMessage());
        }
        return response;
    }
}
