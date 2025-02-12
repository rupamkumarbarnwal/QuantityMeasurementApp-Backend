package com.app.quantityMeasurement.unit;

public interface IMeasurable {

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double value);

    boolean supportsArithmetic();

    String getUnitName();

    String getMeasurementType();

    IMeasurable getUnitInstance(String unitName);

    default void validateOperationSupport(String operationName) {
        if (!supportsArithmetic()) {
            throw new UnsupportedOperationException(
                    "Operation '" + operationName +
                    "' is not supported for measurement type: " +
                    getMeasurementType());
        }
    }

    public static void main(String[] args) {
        System.out.println("IMeasurable");
    }
}