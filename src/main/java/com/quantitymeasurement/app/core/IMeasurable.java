package com.quantitymeasurement.app.core;

public interface IMeasurable {

    double toBaseUnit(double value);

    double fromBaseUnit(double value);

    default boolean supportsArithmetic() {
        return true;
    }

    default void validateOperationSupport(String operation) {
    }

    default String getMeasurementType() {
        return this.getClass().getSimpleName();
    }
}