package com.whales.eplant.dto.request.makeUp;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Attributes specific to a MakeUp vendor")
public class MakeUpAttributes extends VendorRequest {
    @Schema(description = "List of makeup styles offered", example = "[\"Natural\", \"Glam\"]")
    private List<String> makeupStyles;

    @NotNull
    @Schema(description = "Number of people the makeup artist can service", example = "5")
    private int numberOfPeople;

    @NotNull
    @Schema(description = "Indicates if trial makeup sessions are offered", example = "true")
    private boolean offersTrialSession;

    @Schema(description = "List of products used by the makeup artist", example = "[\"MAC\", \"NARS\"]")
    private List<String> productsUsed;

    @Schema(description = "Duration of each makeup session", example = "1 hour")
    private String durationPerSession;
}