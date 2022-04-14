package com.vnpay.isp.be.config;

import com.vnpay.isp.be.common.Utils;
import com.vnpay.isp.be.dto.response.Response;
import com.vnpay.isp.be.enums.JwtErrorCode;
import com.vnpay.isp.be.service.JwtAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/26/2020
 **/
@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtAuthService jwtAuthService;

    @Autowired
    String headerCookieAuth;

    @Autowired
    String queryAccessToken;

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = null;
        String accessToken = httpRequest.getParameter(queryAccessToken);
        Cookie authCookie = Utils.getCookieByName(httpRequest, headerCookieAuth);
        if (authCookie != null) {
            jwtToken = authCookie.getValue();
        }

        Response<String> response = jwtAuthService.validateJwt(jwtToken, accessToken);

        if (JwtErrorCode.VALID.getCode().equals(response.getRspCode())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(accessToken, null, emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            if (JwtErrorCode.EXPIRED.getCode().equals(response.getRspCode())) {
                httpRequest.setAttribute("error", "expired");
            }
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(httpRequest, httpResponse);
    }
}
