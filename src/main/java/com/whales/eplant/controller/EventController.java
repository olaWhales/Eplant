package com.whales.eplant.controller;

import com.whales.eplant.dto.request.event.EventRegistrationRequest;
import com.whales.eplant.dto.response.event.EventRegistrationResponse;
import com.whales.eplant.services.Event.EventRegistrationMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@Tag(name = "Event Registration", description = "Endpoints for registering events")
public class EventController {
    @Autowired
    private EventRegistrationMethod eventRegistrationService;

    @Operation(
            summary = "Register a New Event",
            description = "Allows an authenticated user to register a new event with custom start and end times."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Event registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventRegistrationResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request or user not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerEvent(@Valid @RequestBody EventRegistrationRequest request) {
        try {
            EventRegistrationResponse response = eventRegistrationService.registerEvent(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}