package com.whales.eplant.dto.request.LoginRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password ;
    private String confirmPassword;
}
