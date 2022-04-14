package com.vnpay.isp.be.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/14/2020
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IspTxnDto {
    private double amount;
    private String currCode;
    private String orderReference;
    private String tmnCode;
    private String issuerCode;
    private String issuerName;
    private String scheme;
    private double recurringAmount;
    private String recurringFrequency;
    private int recurringNumberOfIsp;
    private String orderInfo;
    private String locale;
    private double totalIspAmount;
    private String ispPayMethod;
    private String tmnLogoImg;
    private String tmnName;
    private String tranType;
    private String command;

    private Boolean notHaveCardHolder;
    private Boolean notHaveIdentityCode;
    private Boolean notHaveMobile;
    private Boolean notHaveEmail;
    private Boolean notHaveAddress;
    private Boolean notHaveCity;
    private Boolean notHaveCountry;
}
