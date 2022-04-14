package com.vnpay.isp.be.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * 12/8/2020
 **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {
    private String rspCode;
    private String rspMsg;
    private String rspAlias;
    private T data;
    private Object errors;
}
