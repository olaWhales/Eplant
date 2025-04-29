package com.whales.eplant.services.Users;

import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.dto.request.registrationRequest.FullName;
import com.whales.eplant.dto.request.registrationRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.registrationResponse.UserRegistrationResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.whales.eplant.utility.Utility.*;

@Service
@AllArgsConstructor
public class UserRegistrationMethod implements UserRegistration{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder ;

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        boolean userExist = userRepository.existsByEmail(userRegistrationRequest.getEmail());
        if (userExist) {
            return UserRegistrationResponse.builder().message(USER_ALREADY_EXISTS).build();
        }
        Users user = Users.builder().
                firstName(userRegistrationRequest.getFirstName()).
                lastName(userRegistrationRequest.getLastName()).
                email(userRegistrationRequest.getEmail()).
                password(passwordEncoder.encode(userRegistrationRequest.getPassword())).
                build();

        if (!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword())) {
            return UserRegistrationResponse.builder().message(PASSWORD_NOT_MATCH).build();
        } else {
            Users users = userRepository.save(user);

            FullName fullname = FullName.builder().
                    firstName(users.getFirstName()).
                    lastName(user.getLastName()).
                    build();

            return UserRegistrationResponse.builder().
                    fullName(fullname).
                    message(USER_CREATED_MESSAGE).
                    build();
        }
    }
}
