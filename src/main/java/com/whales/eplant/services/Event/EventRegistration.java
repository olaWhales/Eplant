package com.whales.eplant.services.Event;

import com.whales.eplant.data.model.Event;
import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.response.event.EventRegistrationResponse;

public interface EventRegistration {
    EventRegistrationResponse registerEvent(EventRegistrationRequest request);
}
