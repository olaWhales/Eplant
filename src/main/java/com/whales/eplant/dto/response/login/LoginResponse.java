package com.whales.eplant.dto.response.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;
    private String message ;
}
