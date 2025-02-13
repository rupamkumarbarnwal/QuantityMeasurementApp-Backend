package com.quantitymeasurement.app.units;

import com.quantitymeasurement.app.core.IMeasurable;
import com.quantitymeasurement.app.core.SupportsArithmetic;

public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084),
    METERS(3.28084);

    private final double conversionFactor;

    SupportsArithmetic supportsArithmetic = () -> true;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double toBaseUnit(double value) {
        return value * conversionFactor;
    }

    public double fromBaseUnit(double value) {
        return value / conversionFactor;
    }

    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }
}