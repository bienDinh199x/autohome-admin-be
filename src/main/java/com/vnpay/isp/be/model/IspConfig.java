package com.vnpay.isp.be.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/11/2020
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IspConfig {
    private long ispConfigId;
    private String binCode;
    private String issuerCode;
    private String scheme;
    private String recurringFrequency;
    private int recurringNumberOfIsp;
    private String statementDate;
    private String status;
    private String isActiveIsp;
    private double minimumPayment;
}
