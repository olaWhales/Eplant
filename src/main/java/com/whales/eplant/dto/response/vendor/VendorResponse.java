package com.whales.eplant.dto.response.vendor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Response payload for vendor registration")
public class VendorResponse {

    @Schema(description = "Status message indicating registration success", example = "Vendor registered successfully")
    private String message;
}