package com.whales.eplant.dto.request.caterer;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CatererAttributes extends VendorRequest {
    private List<String> themeOptions;
    private boolean offerTasting ;
    private boolean deliveryIncluded;
    private List<String> dietaryConsiderations;
    private int numberOfMeals;
    private Long vendor ;

}
