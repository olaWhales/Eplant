package com.whales.eplant.controller;//package com.whales.eplant.controller;
//
//import com.whales.eplant.dto.request.vendor.VendorRequest;
//import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/Vendor/")
//@AllArgsConstructor
//public class VendorController {
//    private final VendorRegistrationMethod vendorRegistrationMethod;
//
//    @PostMapping("/vendor_registration/")
//    public ResponseEntity <?> vendorRegister(@RequestBody VendorRequest vendorRequest) {
//        try {
//            return new ResponseEntity<>(vendorRegistrationMethod.vendorRegistration(vendorRequest), HttpStatus.CREATED);
//        }catch(Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}

import com.whales.eplant.dto.request.vendor.VendorRequest;
import com.whales.eplant.dto.response.vendor.VendorResponse;
import com.whales.eplant.services.Vendor.VendorRegistrationMethod;
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
@RequestMapping("/Vendor/")
@AllArgsConstructor
public class VendorController {
    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
    private VendorRegistrationMethod vendorRegistrationMethod;

    @PostMapping("/vendor_registration")
    public ResponseEntity<VendorResponse> vendorRegister(@Valid @RequestBody VendorRequest vendorRequest) {
        logger.info("Received vendor registration request: role={}, description={}",
                vendorRequest.getRole(), vendorRequest.getDescription());
        VendorResponse response = vendorRegistrationMethod.vendorRegistration(vendorRequest);
        logger.info("Vendor registration successful for role: {}", vendorRequest.getRole());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}