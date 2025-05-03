package com.whales.eplant.dto.request.mc;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Attributes specific to an MC vendor")
public class McAttributes extends VendorRequest {
    @NotNull
    @Schema(description = "Indicates if dress code attire is provided by the MC", example = "true")
    private boolean dressCodeIncluded;

    @Schema(description = "Languages the MC can perform in", example = "English, Spanish")
    private String languageOptions;

    @Schema(description = "Duration of the MC's performance", example = "3 hours")
    private String performanceDuration;

    @Schema(description = "Type of event the MC specializes in", example = "Weddings")
    private String eventTypeSpecialist;
}