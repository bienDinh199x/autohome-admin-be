package com.ecom.be.service;

import com.ecom.be.dto.request.UserLoginRequest;
import com.ecom.be.dto.request.UserRegisterRequest;
import com.ecom.be.dto.response.Response;
import com.ecom.be.entity.Users;

public interface UsersService {
    Response<Users> login(UserLoginRequest request);

    Response<Users> register(UserRegisterRequest userRegisterRequest);

    Response<String> logout(String authenRequest);

    Response<Users> getUserInfo(String userName);

    Response<String> updateUserInfo(String authenRequest, String userInfo);
}
