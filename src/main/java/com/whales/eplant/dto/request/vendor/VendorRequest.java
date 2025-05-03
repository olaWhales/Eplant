package com.whales.eplant.dto.request.vendor;

import com.whales.eplant.data.model.Role;
import com.whales.eplant.dto.request.decorator.DecoratorAttributes;
import com.whales.eplant.dto.request.makeUp.MakeUpAttributes;
import com.whales.eplant.dto.request.caterer.CatererAttributes;
import com.whales.eplant.dto.request.dj.DjAttributes;
import com.whales.eplant.dto.request.mc.McAttributes;
import com.whales.eplant.dto.request.photographer.PhotographerAttributes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Request payload for registering a vendor")
public class VendorRequest {
    @Schema(description = "Price of the vendor's services", example = "1500.00")
    private Double price;

    @Schema(description = "Description of the vendor's services", example = "Professional wedding decoration services")
    private String description;

    @Schema(description = "Bonus amount offered by the vendor", example = "200.00")
    private Double bonus;

    @NotNull
    @Schema(description = "Availability of the vendor", example = "true")
    private boolean availability;

    @NotNull
    @Schema(description = "Role of the vendor", example = "DECORATOR")
    private Role role;



    @Schema(description = "Attributes specific to a Decorator vendor. Required if role is DECORATOR")
    private DecoratorAttributes decoratorAttributes;

    @Schema(description = "Attributes specific to a DJ vendor. Required if role is DJ")
    private DjAttributes djAttributes;

    @Schema(description = "Attributes specific to an MC vendor. Required if role is MC")
    private McAttributes mcAttributes;

    @Schema(description = "Attributes specific to a Caterer vendor. Required if role is CATERER")
    private CatererAttributes catererAttributes;

    @Schema(description = "Attributes specific to a MakeUp vendor. Required if role is MAKEUP")
    private MakeUpAttributes makeUpAttributes;

    @Schema(description = "Attributes specific to a Photographer vendor. Required if role is PHOTOGRAPHER")
    private PhotographerAttributes photographerAttributes;

    public BigDecimal getPriceAsBigDecimal() {
        return price != null ? BigDecimal.valueOf(price) : BigDecimal.ZERO;
    }

    public BigDecimal getBonusBigDecimal() {
        return bonus != null ? BigDecimal.valueOf(bonus) : BigDecimal.ZERO;
    }
}