package com.autohome.be.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

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
