package com.whales.eplant.dto.request.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Schema(description = "Request payload for registering an event")
public class EventRegistrationRequest {
    @NotBlank
    @Schema(description = "Name of the event", example = "Spring Planting Workshop")
    private String eventName;

    @NotBlank
    @Schema(description = "Type of the event", example = "WORKSHOP")
    private String eventType;

    @NotBlank
    @Schema(description = "Location of the event", example = "Community Farm, Lagos")
    private String location;

    @Schema(description = "Description of the event", example = "A workshop on sustainable planting")
    private String description;

    @Schema(description = "Duration of the event in hours", example = "5")
    private Integer hour;

    @NotNull
    @Schema(description = "Start time of the event", example = "2025-05-01T09:00:00")
    private String startTime;

    @NotNull
    @Schema(description = "End time of the event", example = "2025-05-01T14:00:00")
    private String endTime;


}