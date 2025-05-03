package com.whales.eplant.dto.request.photographer;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Attributes specific to a Photographer vendor")
public class PhotographerAttributes{

    @NotBlank(message = "No of photographer required")
    @Schema(description = "Number of photographers provided", example = "2")
    private int no_Of_photographer;

    @NotNull
    @Schema(description = "Indicates if a photo album is included in the service", example = "true")
    private boolean albumIncluded;

    @NotNull
    @Schema(description = "Indicates if drone photography is included", example = "false")
    private boolean droneIncluded;

    @Schema(description = "Estimated delivery time for photos", example = "2 weeks")
    private String deliveryTime;
}