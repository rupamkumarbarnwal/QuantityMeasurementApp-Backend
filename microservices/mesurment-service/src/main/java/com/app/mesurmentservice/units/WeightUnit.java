package com.app.mesurmentservice.units;


import com.app.mesurmentservice.IMeasurable;
import com.app.mesurmentservice.SupportsArithmetic;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    TONNE(1000);

    private final double conversionFactor;

    SupportsArithmetic supportsArithmetic = () -> true;

    WeightUnit(double conversionFactor) {
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