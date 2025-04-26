package com.whales.eplant.services.Users;

import com.whales.eplant.dto.request.LoginRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.loginResponse.UserRegistrationResponse;

public interface UserRegistration {
    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest);
}
