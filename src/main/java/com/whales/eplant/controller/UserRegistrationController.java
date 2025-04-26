package com.whales.eplant.controller;

import com.whales.eplant.dto.request.LoginRequest.UserRegistrationRequest;
import com.whales.eplant.services.Users.UserRegistration;
import com.whales.eplant.services.Users.UserRegistrationMethod;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Users/")
@AllArgsConstructor
public class UserRegistrationController {
    private final UserRegistrationMethod userRegistration ;

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@RequestBody UserRegistrationRequest  userRegistrationRequest) {
        try{
            return new ResponseEntity<>(userRegistration.registerUser(userRegistrationRequest), HttpStatus.CREATED);
        }catch(Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
