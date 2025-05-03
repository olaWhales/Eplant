package com.whales.eplant.dto.request.dj;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Attributes specific to a DJ vendor")
public class DjAttributes {
    @NotNull
    @Schema(description = "Indicates if lighting is included in the DJ's service", example = "true")
    private boolean lightingIncluded;

    @NotNull
    @Schema(description = "Number of speakers provided by the DJ", example = "4")
    private int numberOfSpeakers;

    @Schema(description = "Duration of the DJ's performance", example = "4 hours")
    private String performanceDuration;

    @NotNull
    @Schema(description = "Number of microphones provided by the DJ", example = "2")
    private int numberOfMicrophones;

    @Schema(description = "List of music genres the DJ specializes in", example = "[\"Pop\", \"Hip-Hop\"]")
    private List<String> musicGenres;
}