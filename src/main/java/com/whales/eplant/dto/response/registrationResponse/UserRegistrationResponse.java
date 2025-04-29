package com.whales.eplant.dto.response.registrationResponse;

import com.whales.eplant.dto.request.registrationRequest.FullName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response object for successful user registration, containing the registered user's full name and a success message")
public class UserRegistrationResponse {

    @Schema(
            description = "The full name of the registered user, including first and last names",
            implementation = FullName.class,
            required = true
    )
    private FullName fullName;

    @Schema(
            description = "A message indicating the result of the registration",
            example = "User registered successfully",
            required = true
    )
    private String message;
}