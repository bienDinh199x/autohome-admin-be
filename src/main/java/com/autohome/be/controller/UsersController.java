package com.autohome.be.controller;

import com.autohome.be.dto.request.UserRegisterRequest;
import com.autohome.be.dto.response.Response;
import com.autohome.be.entity.Users;
import com.autohome.be.service.UsersService;
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
    public ResponseEntity<Response<String>> login() {
        log.info("login");
        Response<String> response = new Response<>();
        response.setRspCode("00");
        response.setRspMsg("success");
        response.setData("token");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Users>> register(@RequestBody UserRegisterRequest registerRequest) {
        log.info("register");
        Response<Users> response = new Response<>();
        try {
            return ResponseEntity.ok(usersService.register(registerRequest));
        } catch (Exception e) {
            response.setRspCode("99");
            response.setRspMsg("System error");
            log.error("REGISTER FAIL ,  Ex ", e);
        }
        return ResponseEntity.ok(response);
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
