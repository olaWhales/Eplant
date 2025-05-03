package com.whales.eplant.dto.request.caterer;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Attributes specific to a Caterer vendor")
public class CatererAttributes extends VendorRequest {
    @Schema(description = "List of theme options offered by the caterer", example = "[\"Italian\", \"Vegan\"]")
    private List<String> themeOptions;

    @NotNull
    @Schema(description = "Indicates if the caterer offers tasting sessions", example = "true")
    private boolean offerTasting;

    @NotNull
    @Schema(description = "Indicates if delivery is included in the service", example = "true")
    private boolean deliveryIncluded;

    @Schema(description = "List of dietary considerations", example = "[\"Vegan\", \"Gluten-Free\"]")
    private List<String> dietaryConsiderations;

    @NotNull
    @Schema(description = "Number of meals the caterer can provide", example = "100")
    private int numberOfMeals;

    @Schema(description = "Identifier for the vendor", example = "123")
    private Long vendor;
}