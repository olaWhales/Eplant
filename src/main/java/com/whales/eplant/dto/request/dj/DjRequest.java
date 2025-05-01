package com.whales.eplant.dto.request.dj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
//@AllArgsConstructor
public class DjRequest {
    private boolean lightingIncluded;
    private int numberOfSpeakers;
    private String performanceDuration;
    private int numberOfMicrophones;
    private List<String> musicGenres;
}
