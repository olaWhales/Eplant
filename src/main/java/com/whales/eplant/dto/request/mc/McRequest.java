//package com.whales.eplant.dto.request.mc;
//
//import lombok.Builder;
//import lombok.Data;
//
//@Data
//@Builder
//public class McRequest {
//    private String languageOptions;
//    private String eventTypeSpecialist;
//    private String performanceDuration;
//    private boolean dressCodeIncluded ;
//}
package com.whales.eplant.dto.request.mc;

import com.whales.eplant.data.model.Role;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class McRequest {
    // Common vendor fields
    private BigDecimal price;
    private String description;
    private BigDecimal bonus;
    private boolean availability;
    private Role role;

    // MC-specific fields
    private boolean dressCodeIncluded;
    private String languageOptions;
    private String performanceDuration;
    private String eventTypeSpecialist;
}