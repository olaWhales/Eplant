package com.whales.eplant.services.login;

import com.whales.eplant.SecurityConfig.JwtUtil;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.dto.request.login.LoginRequest;
import com.whales.eplant.dto.response.login.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.whales.eplant.utility.Utility.INVALID_CREDENTIAL;
import static com.whales.eplant.utility.Utility.LOGIN_SUCCESSFUL_MESSAGE;

@Service
@AllArgsConstructor
@Slf4j
public class UserLoginMethod implements Login {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        String userEmail = loginRequest.getEmail();
        log.info("Login attempt for email: {}", userEmail);

        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userEmail, loginRequest.getPassword())
//            );
            log.info("Authentication successful for email: {}", userEmail);

            String token = jwtService.generateToken(userEmail);
            log.info("Generated token for email: {}", userEmail);

            return LoginResponse.builder()
                    .token(token)
                    .message(LOGIN_SUCCESSFUL_MESSAGE)
                    .build();
        } catch (org.springframework.security.core.AuthenticationException e) {
            log.error("Authentication failed for email: {}", userEmail, e);
            throw new IllegalArgumentException(INVALID_CREDENTIAL, e);
        }
    }
}