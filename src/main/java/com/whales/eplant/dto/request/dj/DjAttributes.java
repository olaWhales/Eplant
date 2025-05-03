package com.whales.eplant.dto.request.dj;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
//@AllArgsConstructor
public class DjAttributes extends VendorRequest {
    private boolean lightingIncluded;
    private int numberOfSpeakers;
    private String performanceDuration;
    private int numberOfMicrophones;
    private List<String> musicGenres;
}
