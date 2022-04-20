package com.autohome.be.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RspObj {
    private String code;
    private String message;
    private String alias;
    private String data;
    private String bankResponData;
    private boolean isRedirect;
    private String redirectUrl;
}
