package com.autohome.be.service.impl;

import com.autohome.be.dto.response.Response;
import com.autohome.be.repository.UsersRepository;
import com.autohome.be.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public Response<String> login(String authenRequest) {
        return null;
    }
}
