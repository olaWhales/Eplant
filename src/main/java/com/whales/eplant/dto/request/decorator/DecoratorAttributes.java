package com.whales.eplant.dto.request.decorator;

import com.whales.eplant.dto.request.vendor.VendorRequest;

import java.util.List;

public class DecoratorAttributes extends VendorRequest {
    private List<String> themeOptions;
    private int numberOfVenues;
    private boolean lightingIncluded;
    private boolean flowersIncluded;
    private boolean customDesign;

}
