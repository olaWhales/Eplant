package com.whales.eplant.services.Caterer;

import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.dto.request.caterer.CatererRegistrationRequest;
import com.whales.eplant.dto.response.caterer.CatererRegistrationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatererRegistrationMethod implements CatererRegistration {
    private final UserRepository userRepository;

    @Override
    public CatererRegistrationResponse registration(CatererRegistrationRequest catererRegistrationRequest) {
//        boolean caterer = userRepository.existsByEmail(catererRegistrationRequest.getEmail);
        return null;
    }
}
