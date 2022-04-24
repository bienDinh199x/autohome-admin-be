package com.autohome.be.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardRequest {

    @NotNull
    @Size(min = 15, max = 19)
    private String cardNumber;

    @NotNull
    @Size(min = 15, max = 19)
    private String cardNumberMask;

    @NotNull
    @Size(min = 5, max = 5)
    private String cardDate;

    @NotNull
    @Size(min = 3, max = 4)
    private String cvcCvv;

    @Size(min = 1, max = 200)
    private String cardHolder;

    @Size(min = 1, max = 20)
    private String identityCode;

    @Size(min = 10, max = 20)
    private String mobile;

    @Email
    @Size(min = 1, max = 255)
    private String email;

    @Size(min = 1, max = 255)
    private String address;

    @Size(min = 1, max = 255)
    private String city;

    @Size(min = 2, max = 2)
    private String country;
}
