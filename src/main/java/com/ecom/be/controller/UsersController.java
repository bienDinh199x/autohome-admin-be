package com.ecom.be.controller;

import com.ecom.be.common.Utils;
import com.ecom.be.dto.request.UserLoginRequest;
import com.ecom.be.dto.request.UserRegisterRequest;
import com.ecom.be.dto.response.Response;
import com.ecom.be.entity.Users;
import com.ecom.be.enums.UserResponse;
import com.ecom.be.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {

    @Autowired
    UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<Response<Users>> login(@RequestBody UserLoginRequest request) {
        log.info("login");
        Response<Users> response = new Response<>();
        try {
            return ResponseEntity.ok(usersService.login(request));
        }catch (Exception e){
            response.setRspCode(UserResponse.USER_NOT_LOGIN.getCode());
            response.setRspMsg(UserResponse.USER_NOT_LOGIN.getDesc());
            log.error("REGISTER FAIL ,  Ex ", e);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Users>> register(@RequestBody UserRegisterRequest request) {
        Response<Users> response = new Response<>();
        try {
            log.info("BEGIN REGISTER USER {}", Utils.toJson(request));
            return ResponseEntity.ok(usersService.register(request));
        } catch (Exception e) {
            response.setRspCode(UserResponse.USER_NOT_REGISTER.getCode());
            response.setRspMsg(UserResponse.USER_NOT_REGISTER.getDesc());
            log.error("REGISTER FAIL ,  Ex ", e);
        }
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<Response<Users>> getUserInfo(@RequestParam("userName") String userName) {
        log.info("getUserInfo");
        Response<Users> response = new Response<>();
        try {
            return ResponseEntity.ok(usersService.getUserInfo(userName));
        } catch (Exception e) {
            response.setRspCode("99");
            response.setRspMsg("System error");
            log.error("GET USER INFO FAIL ,  Ex ", e);
        }
        return ResponseEntity.ok(response);
    }

}
