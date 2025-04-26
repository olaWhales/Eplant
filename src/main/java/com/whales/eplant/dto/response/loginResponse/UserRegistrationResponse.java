package com.whales.eplant.dto.response.loginResponse;

import com.whales.eplant.dto.request.LoginRequest.FullName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationResponse {

    private FullName fullName ;
    private String message ;

}
