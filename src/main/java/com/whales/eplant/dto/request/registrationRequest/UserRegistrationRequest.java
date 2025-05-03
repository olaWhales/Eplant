package com.whales.eplant.dto.request.registrationRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationRequest {

    @NotBlank(message = "First eventName is required")
    @Size(min = 2, max = 50, message = "First eventName must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last eventName is required")
    @Size(min = 2, max = 50, message = "Last eventName must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}