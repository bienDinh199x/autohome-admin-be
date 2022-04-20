package com.autohome.be.service;

import com.autohome.be.dto.request.UserRegisterRequest;
import com.autohome.be.dto.response.Response;
import com.autohome.be.entity.Users;

public interface UsersService {
    Response<String> login(String authenRequest);

    Response<Users> register(UserRegisterRequest userRegisterRequest);

    Response<String> logout(String authenRequest);

    Response<Users> getUserInfo(String userName);

    Response<String> updateUserInfo(String authenRequest, String userInfo);
}
