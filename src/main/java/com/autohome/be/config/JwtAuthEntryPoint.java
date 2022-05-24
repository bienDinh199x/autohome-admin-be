package com.autohome.be.config;

import com.autohome.be.common.Utils;
import com.autohome.be.dto.response.Response;
import com.autohome.be.enums.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AuthenticationException authException) throws IOException, ServletException {
        Response<String> response = new Response<>();
        final String error = (String) httpRequest.getAttribute("error");
        httpResponse.setContentType("application/json");
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        if ("expired".equals(error)) {
            response.setRspCode(ErrorCode.TOKEN_EXPIRED.getCode());
            response.setRspMsg(ErrorCode.TOKEN_EXPIRED.getMessage());
            response.setRspAlias(ErrorCode.TOKEN_EXPIRED.getAlias());
        } else {
            response.setRspCode(ErrorCode.INVALID_TOKEN.getCode());
            response.setRspMsg(ErrorCode.INVALID_TOKEN.getMessage());
            response.setRspAlias(ErrorCode.INVALID_TOKEN.getAlias());
        }
        httpResponse.getOutputStream().print(Utils.toJson(response));
    }
}
