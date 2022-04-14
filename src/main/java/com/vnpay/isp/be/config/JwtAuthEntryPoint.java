package com.vnpay.isp.be.config;

import com.vnpay.isp.be.common.Utils;
import com.vnpay.isp.be.dto.response.Response;
import com.vnpay.isp.be.enums.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/28/2020
 **/
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenticationException authException) throws IOException, ServletException {
        Response<String> response = new Response<>();
        final String error = (String) httpRequest.getAttribute("error");
        httpResponse.setContentType("application/json");
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if ("expired".equals(error)) {
            response.setRspCode(ErrorCode.ISP_TXN_EXPIRED.getCode());
            response.setRspMsg(ErrorCode.ISP_TXN_EXPIRED.getMessage());
            response.setRspAlias(ErrorCode.ISP_TXN_EXPIRED.getAlias());
        } else {
            response.setRspCode(ErrorCode.ISP_TXN_NOT_FOUND.getCode());
            response.setRspMsg(ErrorCode.ISP_TXN_NOT_FOUND.getMessage());
            response.setRspAlias(ErrorCode.ISP_TXN_NOT_FOUND.getAlias());
        }
        httpResponse.getOutputStream().print(Utils.toJson(response));
    }
}
