package com.whales.eplant.controller;

import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Vendor")
@Tag(name = "Vendor Registration", description = "Endpoints for registering vendors")
@AllArgsConstructor
public class VendorController {
    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
    private VendorRegistrationMethod vendorRegistrationMethod;

    @Operation(
            summary = "Register a New Vendor",
            description = "Allows registration of a new vendor with general and role-specific attributes (Decorator, DJ, MC, Caterer, MakeUp, Photographer)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Vendor registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/vendor_registration")
    public ResponseEntity<?> vendorRegister(@Valid @RequestBody VendorRequest vendorRequest) {
        try {
            return new ResponseEntity<>(vendorRegistrationMethod.vendorRegistration(vendorRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}