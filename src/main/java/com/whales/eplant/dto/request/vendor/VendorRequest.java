package com.whales.eplant.dto.request.vendor;

import com.whales.eplant.data.model.Role;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class VendorRequest {
    private BigDecimal price;
    private String description;
    private BigDecimal bonus;
    private boolean availability;
    private Role role;
}
