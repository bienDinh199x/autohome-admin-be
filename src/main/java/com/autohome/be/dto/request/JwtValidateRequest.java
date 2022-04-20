package com.autohome.be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtValidateRequest {
    private String accessToken;
    private String jwtToken;
}
