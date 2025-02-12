package com.app.quantityMeasurement.unit;

public enum LengthUnit implements IMeasurable {

    INCHES(1.0),
    FEET(12.0),
    YARD(36.0),
    CENTIMETER(0.393701),
    METER(39.3701),
    KILOMETER(39370.1),
    MILE(63360.0);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        return value / conversionFactor;
    }

    @Override
    public boolean supportsArithmetic() {
        return true;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType() {
        return "LengthUnit";
    }

    @Override
    public IMeasurable getUnitInstance(String unitName) {
        try {
            return LengthUnit.valueOf(
                    unitName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown LengthUnit: " + unitName);
        }
    }

    public static void main(String[] args) {
        System.out.println("LengthUnit");
        System.out.println("1 FEET in INCHES: " +
                LengthUnit.FEET.convertToBaseUnit(1.0));
        System.out.println("12 INCHES in FEET: " +
                LengthUnit.FEET.convertFromBaseUnit(12.0));
    }
}