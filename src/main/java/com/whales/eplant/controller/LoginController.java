package com.whales.eplant.controller;

import com.whales.eplant.dto.request.login.LoginRequest;
import com.whales.eplant.services.login.Login;
import com.whales.eplant.services.login.UserLoginMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {
    private final UserLoginMethod login ;


    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try{
            return new ResponseEntity<>(login.login(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
