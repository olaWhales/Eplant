package com.whales.eplant.services.Vendor;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;

public interface VendorRegistration {
    VendorResponse vendorRegistration(VendorRequest request);
}
