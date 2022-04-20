package com.autohome.be.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;
import java.util.regex.Pattern;

@Slf4j
public class Utils {

    public static boolean isNullOrEmpty(String data) {
        return data == null || data.trim().length() == 0;
    }

    public static String toJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static <T> T fromJson(String json, TypeReference<T> type) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, type);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipaddr = request.getHeader("X-FORWARDED-FOR");
        if (ipaddr == null || ipaddr.isEmpty()) {
            ipaddr = request.getRemoteAddr();
        }
        return ipaddr.split(",")[0];
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName()))
                    return cookie;
            }
        }
        return null;
    }

    /**
     * @author vinhnt
     */
    public static String unAccent(String strInput) {
        try {
            String temp = Normalizer.normalize(strInput, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
        } catch (Exception ex) {
            log.error("Remove Unicode Exception: ", ex);
        }
        return strInput;
    }

    public static String maskCardNumber(String cardNo) {
        if (cardNo != null && cardNo.trim().length() > 10) {
            cardNo = cardNo.trim();
            StringBuilder binCode = new StringBuilder(cardNo.substring(0, 6));
            String last4 = cardNo.substring(cardNo.length() - 4);
            int maskLength = cardNo.length() - 10;
            binCode.append("x".repeat(Math.max(0, maskLength)));
            return binCode + last4;
        } else {
            return cardNo;
        }
    }
}
