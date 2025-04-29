package com.whales.eplant.controller;

import com.whales.eplant.dto.request.registrationRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.registrationResponse.UserRegistrationResponse;
import com.whales.eplant.services.Users.UserRegistrationMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Users")
@Tag(name = "User Registration", description = "Endpoints for registering new users")
@AllArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationMethod userRegistration;

    @Operation(
            summary = "Register a New User",
            description = "Allows a new user to register by providing their first name, last name, email, password, and password confirmation."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data (e.g., missing fields, invalid email, or password mismatch)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict (e.g., email already registered)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            )
    })
    @PostMapping("/registration")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        try {
            UserRegistrationResponse response = userRegistration.registerUser(userRegistrationRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}