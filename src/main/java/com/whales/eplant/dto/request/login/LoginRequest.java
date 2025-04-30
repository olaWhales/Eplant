package com.whales.eplant.dto.request.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing the login request payload.
 *
 * Carries user's email and password from client to server for authentication.
 */
@Data
@Builder
@Schema(description = "Login request containing user's email and password.")
public class LoginRequest {

    @Schema(description = "User's email address.", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "User's password.", example = "password123", required = true)
    private String password;
}
