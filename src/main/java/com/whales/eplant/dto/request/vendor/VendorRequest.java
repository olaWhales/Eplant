package com.whales.eplant.dto.request.vendor;

import com.whales.eplant.data.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
//@Builder
public class VendorRequest {
    @Schema(example = "100.00")
    private Double price;

    @Schema(example = "Master of Ceremonies for weddings")
    private String description;

    @Schema(example = "10.00")
    private Double bonus;

    @Schema(example = "true")
    private boolean availability;

    @Schema(example = "MC", allowableValues = {"MC", "CATERER", "PHOTOGRAPHER", "DJ"})
    private Role role;

    @Schema(example = "{\"dressCodeIncluded\": true, \"languageOptions\": \"English, Spanish\", \"performanceDuration\": \"2 hours\", \"eventTypeSpecialist\": \"Wedding\"}")
    private Map<String, Object> roleAttributes;


    public BigDecimal getPriceAsBigDecimal() {
        return price != null ? BigDecimal.valueOf(price) : BigDecimal.ZERO;
    }

    public BigDecimal getBonusBigDecimal() {
        return bonus != null ? BigDecimal.valueOf(bonus) : BigDecimal.ZERO;
    }



}