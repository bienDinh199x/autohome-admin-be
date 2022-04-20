package com.autohome.be.service;

import com.autohome.be.dto.response.Response;

public interface UsersService {
    Response<String> login(String authenRequest);
}
