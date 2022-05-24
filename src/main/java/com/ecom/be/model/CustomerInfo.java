package com.ecom.be.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerInfo {
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String address1;
    private String city;
    private String country;
    private String stateOrProvince;
    private String zipCode;
    private String mobile;
    private String identityCode;
}
