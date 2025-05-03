//package com.whales.eplant.dto.request.mc;
//
//import lombok.Builder;
//import lombok.Data;
//
//@Data
//@Builder
//public class McAttributes {
//    private String languageOptions;
//    private String eventTypeSpecialist;
//    private String performanceDuration;
//    private boolean dressCodeIncluded ;
//}
package com.whales.eplant.dto.request.mc;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import lombok.Data;

@Data
//@Builder
//@AllArgsConstructor
public class McAttributes extends VendorRequest {
    // Common vendor fields
//    private BigDecimal price;
//    private String description;
//    private BigDecimal bonus;
//    private boolean availability;
//    private Role role;

    // MC-specific fields
    private boolean dressCodeIncluded;
    private String languageOptions;
    private String performanceDuration;
    private String eventTypeSpecialist;
}