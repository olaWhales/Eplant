package com.whales.eplant.controller;

import com.whales.eplant.dto.request.login.LoginRequest;
import com.whales.eplant.services.login.UserLoginMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling user login operations.
 *
 * Exposes an endpoint that accepts user credentials (email and password),
 * delegates authentication logic to the service layer (UserLoginMethod),
 * and returns the generated JWT token upon successful authentication.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginMethod login;

    /**
     * Endpoint to authenticate a user based on provided login credentials.
     *
     * @param request A {@link LoginRequest} object containing user's email and password.
     * @return ResponseEntity containing the JWT token if authentication succeeds,
     *         or an error message if authentication fails.
     */
    @Operation(summary = "User Login", description = "Authenticate user with email and password to receive a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful. JWT token returned.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Internal server error. Authentication failed.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return new ResponseEntity<>(login.login(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
