package com.vnpay.isp.be.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/31/2020
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LangPhrase implements Serializable {
    private int langId;
    private String title;
    private String text;
}
