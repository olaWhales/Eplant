package com.whales.eplant.dto.request.makeUp;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MakeUpAttributes extends VendorRequest {
    private List<String> makeupStyles;
    private int numberOfPeople;
    private boolean offersTrialSession;

    private List<String> productsUsed;
    private String durationPerSession;

}
