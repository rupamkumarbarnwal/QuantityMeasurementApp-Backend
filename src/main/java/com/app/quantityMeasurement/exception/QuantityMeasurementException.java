package com.app.quantityMeasurement.exception;

public class QuantityMeasurementException extends RuntimeException {

    public QuantityMeasurementException(String message) {
        super(message);
    }

    public QuantityMeasurementException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void main(String[] args) {
        System.out.println("QuantityMeasurementException");
    }
}