package com.whales.eplant.services.Users;

import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.dto.request.LoginRequest.FullName;
import com.whales.eplant.dto.request.LoginRequest.UserRegistrationRequest;
import com.whales.eplant.dto.response.loginResponse.UserRegistrationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.whales.eplant.utility.Utility.USER_ALREADY_EXISTS;
import static com.whales.eplant.utility.Utility.USER_CREATED_MESSAGE;

@Service
@AllArgsConstructor
public class UserRegistrationMethod implements UserRegistration{
    private final UserRepository userRepository;

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest userRegistrationRequest) {
        boolean userExist =  userRepository.existsByEmail(userRegistrationRequest.getEmail());
        if (userExist) {
            return UserRegistrationResponse.builder().message(USER_ALREADY_EXISTS).build();
        }
            Users user = Users.builder().
                    firstName(userRegistrationRequest.getFirstName()).
                    lastName(userRegistrationRequest.getLastName()).
                    email(userRegistrationRequest.getEmail()).
                    password(userRegistrationRequest.getPassword()).
                    confirmPassword(userRegistrationRequest.getConfirmPassword()).
                    build();

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
