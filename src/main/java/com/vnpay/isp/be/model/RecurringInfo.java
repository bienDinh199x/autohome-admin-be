package com.vnpay.isp.be.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 3/24/2021
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecurringInfo {
    private int recurringNumberOfIsp;
    private String recurringFrequency;
    private double amount;
    private double recurringAmount;
    private double totalIspAmount;
    private double feeAmount;
    private String currCode;
}
