package com.sunking.CaseStudy.Exception;

public class InventoryNotAvailableException extends RuntimeException {
    public InventoryNotAvailableException(String message) {
        super(message);
    }
}
