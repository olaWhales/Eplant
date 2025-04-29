package com.whales.eplant.dto.request.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {
    private String email ;
    private String password ;
}
