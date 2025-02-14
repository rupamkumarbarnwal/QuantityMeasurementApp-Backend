package com.quantitymeasurement.app.model;

import com.quantitymeasurement.app.core.IMeasurable;

public class QuantityModel<U extends IMeasurable> {

    private double value;
    private U unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }
}