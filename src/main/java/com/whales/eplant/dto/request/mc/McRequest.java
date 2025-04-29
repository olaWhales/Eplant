package com.whales.eplant.dto.request.mc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class McRequest {
    private String languageOptions;
    private String eventTypeSpecialist;
    private String performanceDuration;
    private boolean dressCodeIncluded ;
}
