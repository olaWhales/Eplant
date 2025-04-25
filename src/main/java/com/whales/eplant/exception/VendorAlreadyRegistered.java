package com.whales.eplant.exception;

public class VendorAlreadyRegistered extends RuntimeException {
    public VendorAlreadyRegistered(String message) {
        super(message);
    }
}
