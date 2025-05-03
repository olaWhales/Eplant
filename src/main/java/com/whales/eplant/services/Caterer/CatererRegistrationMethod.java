package com.whales.eplant.services.Caterer;

import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.dto.request.caterer.CatererAttributes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatererRegistrationMethod implements CatererRegistration {
    private final UserRepository userRepository;
}
