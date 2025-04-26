package com.whales.eplant.dto.request.registrationRequest;

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
