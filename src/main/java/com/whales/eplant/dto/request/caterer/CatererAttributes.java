package com.whales.eplant.dto.request.caterer;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Attributes specific to a Caterer vendor")
public class CatererAttributes {

    private List<String> menuOptions;
    private int numberOfMeals;
    private boolean offersTasting;
    @ElementCollection
    private List<String> dietaryConsiderations;
    private boolean deliveryIncluded;

}