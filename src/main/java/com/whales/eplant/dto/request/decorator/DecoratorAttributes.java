package com.whales.eplant.dto.request.decorator;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Attributes specific to a Decorator vendor")
public class DecoratorAttributes extends VendorRequest {
    @Schema(description = "List of theme options offered by the decorator", example = "[\"Rustic\", \"Modern\"]")
    private List<String> themeOptions;

    @NotNull
    @Schema(description = "Number of venues the decorator can service", example = "2")
    private int numberOfVenues;

    @NotNull
    @Schema(description = "Indicates if lighting is included in the service", example = "true")
    private boolean lightingIncluded;

    @NotNull
    @Schema(description = "Indicates if flowers are included in the service", example = "false")
    private boolean flowersIncluded;

    @NotNull
    @Schema(description = "Indicates if custom design services are offered", example = "true")
    private boolean customDesign;
}