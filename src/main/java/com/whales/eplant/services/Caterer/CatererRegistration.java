package com.whales.eplant.services.Caterer;

import com.whales.eplant.dto.request.caterer.CatererRegistrationRequest;
import com.whales.eplant.dto.response.caterer.CatererRegistrationResponse;

public interface CatererRegistration {
    CatererRegistrationResponse registration(CatererRegistrationRequest catererRegistrationRequest);
}
