package com.whales.eplant.services.Mc;

import com.whales.eplant.dto.request.mc.McRequest;
import com.whales.eplant.dto.response.mc.McResponse;

public interface McRegistration {
    McResponse mcResponse(McRequest request);
}
