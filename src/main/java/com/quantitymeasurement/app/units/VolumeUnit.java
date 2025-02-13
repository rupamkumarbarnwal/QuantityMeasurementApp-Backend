package com.quantitymeasurement.app.units;

import com.quantitymeasurement.app.core.IMeasurable;
import com.quantitymeasurement.app.core.SupportsArithmetic;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double conversionFactor;

    SupportsArithmetic supportsArithmetic = () -> true;

    VolumeUnit(double conversionFactor) {
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