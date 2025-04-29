package com.whales.eplant.services.login;

import com.whales.eplant.dto.request.login.LoginRequest;
import com.whales.eplant.dto.response.login.LoginResponse;
import lombok.Data;


public interface Login {
    LoginResponse login(LoginRequest loginRequest);
}
