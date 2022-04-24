package com.autohome.be.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardVerify {
    private long tranId;
    private String bankCode;
    private String token;
    private String cardType;
    private String cardNumber;
    private String cardDate;
    private String ipAddr;
    private String cvv;
    private String cardHolder;
    private String addData;
    private String userAgent;
    private String authType;
    private String authValue;
    private String command;
    //Additional fields
    private String field1;
    private String field2;
    private String field3;
}
