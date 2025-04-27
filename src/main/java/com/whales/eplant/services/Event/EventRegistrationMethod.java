package com.whales.eplant.services.Event;

import com.whales.eplant.data.model.Event;
import com.whales.eplant.data.model.UserPrincipal;
import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.repository.EventRepository;
import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.response.event.EventRegistrationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service for registering events, allowing users to create events with custom start and end times.
 */
@Service
@AllArgsConstructor
public class EventRegistrationMethod implements EventRegistration {

    private final EventRepository eventRepository;
    /**
     * Registers a new event based on the provided request.
     *
     * @param request The event registration request containing event details.
     * @return An EventRegistrationResponse with the registration result.
     * @throws IllegalArgumentException if the user is not authenticated or the request is invalid.
     */
    @Override
    public EventRegistrationResponse registerEvent(@Valid EventRegistrationRequest request) {
        // Validate authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new IllegalArgumentException("User not authenticated");
        }

        // Get authenticated user
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Users user = userPrincipal.users();

        // Validation of  start and end times
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time are required");
        }
        if (request.getEndTime().isBefore(request.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        // Build of event entity
        Event event = Event.builder()
                .name(request.getName())
                .eventType(request.getEventType())
                .location(request.getLocation())
                .description(request.getDescription())
                .hour(request.getHour())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .user(user)
                .build();

        // Save event to database
        Event savedEvent = eventRepository.save(event);

        // Build response
        return EventRegistrationResponse.builder()
                .eventId(savedEvent.getId())
                .message("Event registered successfully")
                .status("SUCCESS")
                .build();
    }
}