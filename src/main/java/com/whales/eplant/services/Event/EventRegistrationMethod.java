//package com.whales.eplant.services.Event;
//
//import com.whales.eplant.data.model.Event;
//import com.whales.eplant.data.model.Users;
//import com.whales.eplant.data.repository.EventRepository;
//import com.whales.eplant.data.repository.UserRepository;
//import com.whales.eplant.dto.request.event.EventRegistrationRequest;
//import com.whales.eplant.dto.response.event.EventRegistrationResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//
//import static com.whales.eplant.utility.Utility.USER_NOT_AUTHENTICATED_MESSAGE;
//
//@Service
//@RequiredArgsConstructor
//public class EventRegistrationMethod {
//
//    private final EventRepository eventRepository;
//    private final UserRepository userRepository;
//
//    public EventRegistrationResponse registerEvent(EventRegistrationRequest request) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
//            throw new IllegalArgumentException(USER_NOT_AUTHENTICATED_MESSAGE);
//        }
//
//        // Validate input
//        if (request.getStartTime() == null || request.getEndTime() == null) {
//            throw new IllegalArgumentException("Start time and end time are required");
//        }
//
//        try {
//            // Parse times
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
//            LocalDate today = LocalDate.now();
//            LocalTime startLocalTime = LocalTime.parse(request.getStartTime().toUpperCase(), formatter);
//            LocalTime endLocalTime = LocalTime.parse(request.getEndTime().toUpperCase(), formatter);
//
//            LocalDateTime startTime = LocalDateTime.of(today, startLocalTime);
//            LocalDateTime endTime = LocalDateTime.of(today, endLocalTime);
//
//            // Get authenticated user
//            String username = authentication.getEventName(); // Email from JWT
//            Users user = userRepository.findByEmail(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//
//            // Create event
//            Event event = Event.builder()
//                    .eventName(request.getEventName())
//                    .eventType(request.getEventType())
//                    .location(request.getLocation())
//                    .description(request.getDescription())
//                    .hour(request.getHour())
//                    .startTime(startTime)
//                    .endTime(endTime)
//                    .user(user)
//                    .build();
//
//            eventRepository.save(event);
//
//            return EventRegistrationResponse.builder()
//                    .message("Event registered successfully")
//                    .build();
//        } catch (DateTimeParseException e) {
//            throw new IllegalArgumentException("Invalid time format. Use hh:mma (e.g., 02:00AM)", e);
//        }
//    }
//}
package com.whales.eplant.services.Event;

import com.whales.eplant.data.model.Event;
import com.whales.eplant.data.model.Users;
import com.whales.eplant.data.repository.EventRepository;
import com.whales.eplant.data.repository.UserRepository;
import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.response.VendorEventNotificationResponse;
import com.whales.eplant.dto.response.event.EventRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import static com.whales.eplant.utility.Utility.USER_NOT_AUTHENTICATED_MESSAGE;

@Service
@RequiredArgsConstructor
public class EventRegistrationMethod implements EventRegistration{

    private static final Logger log = LoggerFactory.getLogger(EventRegistrationMethod.class);
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public EventRegistrationResponse registerEvent(EventRegistrationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            throw new IllegalArgumentException(USER_NOT_AUTHENTICATED_MESSAGE);
        }
        log.info("Registering event: {}", request.getEventName());

        // Validate input
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time are required");
        }

        try {
            // Parse times
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern("hh:mma")
                    .toFormatter(Locale.ENGLISH);
            LocalDate today = LocalDate.now();
            String startTimeStr = request.getStartTime().toUpperCase();
            String endTimeStr = request.getEndTime().toUpperCase();

            LocalTime startLocalTime = LocalTime.parse(startTimeStr, formatter);
            LocalTime endLocalTime = LocalTime.parse(endTimeStr, formatter);

            LocalDateTime startTime = LocalDateTime.of(today, startLocalTime);
            LocalDateTime endTime = LocalDateTime.of(today, endLocalTime);

            // Get authenticated user
            String username = authentication.getName();
            log.info("Authenticated user: {}", username);
            Users user = userRepository.findByEmailWithVendors(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            // Create event
            Event event = Event.builder()
                    .eventName(request.getEventName())
                    .eventType(request.getEventType())
                    .location(request.getLocation())
                    .description(request.getDescription())
                    .hour(request.getHour())
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .build();

            Event event1 = eventRepository.save(event);

//            VendorEventNotificationResponse vendorEventNotificationResponse = VendorEventNotificationResponse.builder()
//                    .eventName(event1.getEventName())
//                    .eventType(event1.getEventType())
//                    .location(event1.getLocation())
//                    .description(event1.getDescription())
//                    .hour(event1.getHour())
//                    .startTime(event1.getStartTime())
//                    .endTime(event1.getEndTime())
//                    .build();


            log.info("Event saved: {}", event.getEventName());

            return EventRegistrationResponse.builder()
                    .message("Event registered successfully")
                    .build();
        } catch (DateTimeParseException e) {
            log.error("Invalid time format: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid time format. Use hh:mma (e.g., 02:00AM)", e);
        }
    }
}