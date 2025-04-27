package com.whales.eplant.dto.response.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Response payload for event registration")
public class EventRegistrationResponse {
    @Schema(description = "ID of the registered event", example = "123")
    private Long eventId;

    @Schema(description = "Response message", example = "Event registered successfully")
    private String message;

    @Schema(description = "Status of the registration", example = "SUCCESS")
    private String status;
}