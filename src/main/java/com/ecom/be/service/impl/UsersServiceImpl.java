package com.autohome.be.service.impl;

import com.autohome.be.common.Utils;
import com.autohome.be.dto.request.UserRegisterRequest;
import com.autohome.be.dto.response.Response;
import com.autohome.be.entity.Users;
import com.autohome.be.enums.UserResponse;
import com.autohome.be.enums.UserStatus;
import com.autohome.be.repository.UsersRepository;
import com.autohome.be.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public Response<String> login(String authenRequest) {
        return null;
    }

    @Override
    public Response<Users> register(UserRegisterRequest userRegisterRequest) {
        Response<Users> response = new Response<>();
        try {
            Users findUser = usersRepository.findByUserName(userRegisterRequest.getUsername());
            if (Utils.isNullOrEmpty(findUser.getUserName())) {

                Users users = new Users();
                users.setUserName(userRegisterRequest.getUsername());
                users.setPwd(userRegisterRequest.getPassword());
                users.setPwdSalt(Utils.getRandomString(6));
                users.setStatus(UserStatus.ACTIVE.getStatusCode());
                usersRepository.save(users);

                response.setRspCode(UserResponse.SUCCESS.getCode());
                response.setRspMsg(UserResponse.SUCCESS.getDesc());
            } else {
                response.setRspCode(UserResponse.USER_EXIST.getCode());
                response.setRspMsg(UserResponse.USER_EXIST.getDesc());
            }
        } catch (Exception e) {
            response.setRspCode(UserResponse.USER_NOT_REGISTER.getCode());
            response.setRspMsg(UserResponse.USER_NOT_REGISTER.getDesc());
            log.info("REGISTER EXCEPTION", e);
        }
        return response;
    }

    @Override
    public Response<String> logout(String authenRequest) {
        return null;
    }

    @Override
    public Response<Users> getUserInfo(String userName) {
        Response<Users> response = new Response<>();
        try {
            Users users = usersRepository.findByUserName(userName);
            response.setRspMsg(UserResponse.SUCCESS.getCode());
            response.setRspMsg(UserResponse.SUCCESS.getDesc());
            response.setData(users);
        } catch (Exception e) {
            response.setRspMsg(UserResponse.USER_NOT_EXIST.getCode());
            response.setRspMsg(UserResponse.USER_NOT_EXIST.getDesc());
            log.info("getUserInfo error", e);
        }
        return response;
    }

    @Override
    public Response<String> updateUserInfo(String authenRequest, String userInfo) {
        return null;
    }
}
