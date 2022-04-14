package com.vnpay.isp.be.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author sonnt2
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IspTxn {
    private String ispTxnId;
    private double amount;
    private String currCode;
    private String orderReference;
    private String tmnCode;
    private String issuerCode;
    private String scheme;
    private String cardType;
    private double feeAmount;
    private double recurringAmount;
    private String recurringFrequency;
    private int recurringNumberOfIsp;
    private String recurringStartDate;
    private long ispConfigId;
    private String orderInfo;
    private String addData;
    private long tranId;
    private String approvalCode;
    private String payStatus;
    private String status;
    private String identityCode;
    private String billToForename;
    private String billToSurname;
    private String billToMobile;
    private String billToEmail;
    private String billToAddress;
    private String billToCity;
    private String billToCountry;
    private Date createdDate;
    private Date paymentDate;
    private String ipAddr;
    private String userAgent;
    private String returnUrl;
    private String cancelUrl;
    private String version;
    private String locale;
    private Date mcDate;
    private Date expDate;
    private String secureHash;
    private String hashType;
    private String ispStatus;
    private double totalIspAmount;
    private String accessToken;
    private String ispPayMethod;
    private String isAcceptedTerms;
    private String tranType;
    private String recurringId;
    private String command;

    // For client
    private String issuerName;
    private long reqId;
    private String tmnLogoImg;
    private String tmnName;
}
