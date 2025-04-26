package com.whales.eplant.services.Users;

import com.whales.eplant.dto.request.registrationRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.registrationResponse.UserRegistrationResponse;

public interface UserRegistration {
    UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest);
}
