package com.vnpay.isp.be.controller;

import com.vnpay.isp.be.common.Utils;
import com.vnpay.isp.be.dto.request.CardRequest;
import com.vnpay.isp.be.dto.response.IspTxnDto;
import com.vnpay.isp.be.dto.response.Response;
import com.vnpay.isp.be.dto.response.VerifyRsp;
import com.vnpay.isp.be.enums.VerifyErrorCode;
import com.vnpay.isp.be.service.RecurringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * @since 11/9/2021
 **/
@Slf4j
@RestController
@RequestMapping("recurring")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecurringController {

    @Autowired
    Validator validator;

    @Autowired
    String headerCookieAuth;

    @Autowired
    RecurringService recurringService;

    @GetMapping("ping")
    public String ping() {
        log.info("test");
        return "pong";
    }

    @GetMapping("login")
    public String login(@RequestParam("userName") String userName) {
        log.info("login");
        if (userName != null && userName.equals("biendt")){
            return "login success";
        }
        return "login success";
    }

    @GetMapping("get")
    public Response<IspTxnDto> getIspTxnDto(@RequestParam String accessToken) {
        return recurringService.getIspTxnDto(accessToken);
    }

    @PostMapping("cancel")
    public Response<String> cancelIspTxn(@RequestParam String accessToken) {
        return recurringService.cancelIspTxn(accessToken);
    }

    @PostMapping(value = "verify")
    public ResponseEntity<Response<VerifyRsp>> verifyCard(@RequestParam String accessToken, @RequestBody CardRequest cardRequest,
                                                          HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Response<VerifyRsp> response = new Response<>();
        try {
            Set<ConstraintViolation<CardRequest>> violations = validator.validate(cardRequest);
            if (violations.size() > 0) { //Validation errors
                Map<String, String> validationErrors = violations.stream()
                        .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), ConstraintViolation::getMessage));
                response.setRspCode(VerifyErrorCode.INVALID_DATA_FORMAT.getCode());
                response.setRspMsg(VerifyErrorCode.INVALID_DATA_FORMAT.getMessage());
                response.setRspAlias(VerifyErrorCode.INVALID_DATA_FORMAT.getAlias());
                response.setErrors(validationErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                String ipAddr = Utils.getIpAddr(httpRequest);
                String userAgent = Utils.getUserAgent(httpRequest);
                response = recurringService.verifyCard(accessToken, cardRequest, ipAddr, userAgent);
                if (VerifyErrorCode.SUCCESS.getCode().equals(response.getRspCode())) {
                    //Delete Auth Cookie
                    Cookie cookie = new Cookie(headerCookieAuth, "");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    httpResponse.addCookie(cookie);
                }
            }
        } catch (Exception ex) {
            log.error("Exception Request verifyCard: accessToken={}", accessToken, ex);
            response.setRspCode(VerifyErrorCode.EXCEPTION.getCode());
            response.setRspMsg(VerifyErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(VerifyErrorCode.EXCEPTION.getAlias());
        }
        return ResponseEntity.ok(response);
    }
}
