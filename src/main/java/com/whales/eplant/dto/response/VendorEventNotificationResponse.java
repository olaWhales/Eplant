package com.whales.eplant.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class VendorEventNotificationResponse {
    private String eventName;
    private String eventType;
    private String location;
    private String description;
    private Integer hour;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
