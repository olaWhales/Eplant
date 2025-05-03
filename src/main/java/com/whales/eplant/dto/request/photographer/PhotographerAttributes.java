package com.whales.eplant.dto.request.photographer;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PhotographerAttributes extends VendorRequest {
    private int no_Of_photographer ;
    private boolean albumIncluded ;
    private boolean droneIncluded ;
    private String deliveryTime ;
}
