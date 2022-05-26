package com.ecom.be.service.impl;

import com.ecom.be.common.Utils;
import com.ecom.be.dto.request.UserLoginRequest;
import com.ecom.be.dto.request.UserRegisterRequest;
import com.ecom.be.dto.response.Response;
import com.ecom.be.entity.Users;
import com.ecom.be.enums.UserResponse;
import com.ecom.be.enums.UserStatus;
import com.ecom.be.repository.UsersRepository;
import com.ecom.be.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public Response<Users> login(UserLoginRequest request) {
        Response<Users> response = new Response<>();
        try {
            if (Utils.isNullOrEmpty(request.getUserName())) {
                log.info("LOGIN FAIL USERNAME EMTRY");
                response.setRspCode(UserResponse.DATA_NOT_FOUNT.getCode());
                response.setRspMsg(UserResponse.DATA_NOT_FOUNT.getDesc());
            } else {
                Users users = usersRepository.findByUserName(request.getUserName());
                if (Utils.isNullOrEmptyObject(users)) {
                    response.setRspCode(UserResponse.USER_NOT_EXIST.getCode());
                    response.setRspMsg(UserResponse.USER_NOT_EXIST.getDesc());
                    log.info("LOGIN FAIL {}", request.getUserName());
                } else {
                    if (users.getPwd().equals(request.getPassword()) && users.getStatus().equals(UserStatus.ACTIVE.getStatusCode())) {
                        response.setRspCode(UserResponse.SUCCESS.getCode());
                        response.setRspMsg(UserResponse.SUCCESS.getDesc());
                        response.setData(users);
                        log.info("LOGIN SUCCESS {}", users);
                    } else {
                        response.setRspCode(UserResponse.USER_IS_LOCKED.getCode());
                        response.setRspMsg(UserResponse.USER_IS_LOCKED.getDesc());
                        response.setData(users);
                        log.info("LOGIN FAIL {} - USER_IS_LOCKED", users);
                    }
                }
            }

        } catch (Exception e) {
            response.setRspCode(UserResponse.FAIL.getCode());
            response.setRspMsg(UserResponse.FAIL.getDesc());
            log.info("LOGIN EXCEPTION", e);
        }
        return response;
    }

    @Override
    public Response<Users> register(UserRegisterRequest userRegisterRequest) {
        Response<Users> response = new Response<>();
        try {
            Users findUser = usersRepository.findByUserName(userRegisterRequest.getUserName());
            if (!Utils.isNullOrEmpty(userRegisterRequest.getUserName()) && Utils.isNullOrEmptyObject(findUser)) {

                Users user = new Users();
                user.setUserName(userRegisterRequest.getUserName());
                user.setPwd(userRegisterRequest.getPassword());
                user.setPwdSalt(Utils.getRandomString(6));
                user.setStatus(UserStatus.ACTIVE.getStatusCode());
                user.setCreatedDate(Utils.getCurrentTime());
                usersRepository.save(user);


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
