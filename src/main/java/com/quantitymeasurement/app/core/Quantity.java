package com.quantitymeasurement.app.core;

import com.quantitymeasurement.app.core.IMeasurable;

public class Quantity<U extends IMeasurable> {

    private double value;
    private U unit;
    private static final double TOLERANCE = 0.0001;

    public Quantity(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double toBaseUnit() {
        return unit.toBaseUnit(value);
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.toBaseUnit(value);
        double converted = targetUnit.fromBaseUnit(base);
        return new Quantity<>(converted, targetUnit);
    }

    private void validateSameType(Quantity<U> other) {
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new RuntimeException("Different unit types not allowed");
        }
    }

    public Quantity<U> add(Quantity<U> other) {
        validateSameType(other);
        unit.validateOperationSupport("addition");

        double result = this.toBaseUnit() + other.toBaseUnit();
        double converted = unit.fromBaseUnit(result);
        return new Quantity<>(converted, unit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        validateSameType(other);
        unit.validateOperationSupport("subtraction");

        double result = this.toBaseUnit() - other.toBaseUnit();
        double converted = unit.fromBaseUnit(result);
        return new Quantity<>(converted, unit);
    }
    public double multiply(Quantity<U> other) {
        validateSameType(other);
        unit.validateOperationSupport("multiplication");

        return this.toBaseUnit() * other.toBaseUnit();
    }

    public double divide(Quantity<U> other) {
        validateSameType(other);
        unit.validateOperationSupport("division");

        return this.toBaseUnit() / other.toBaseUnit();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Quantity)) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < TOLERANCE;
    }

    @Override
    public int hashCode() {
        long baseValue = Double.doubleToLongBits(this.toBaseUnit());
        return (int) (baseValue ^ (baseValue >>> 32));
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }
}