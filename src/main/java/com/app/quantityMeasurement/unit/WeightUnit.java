package com.app.quantityMeasurement.unit;

public enum WeightUnit implements IMeasurable {

    GRAM(1.0),
    KILOGRAM(1000.0),
    TONNE(1000000.0),
    POUND(453.592),
    OUNCE(28.3495);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
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
        return "WeightUnit";
    }

    @Override
    public IMeasurable getUnitInstance(String unitName) {
        try {
            return WeightUnit.valueOf(
                    unitName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown WeightUnit: " + unitName);
        }
    }

    public static void main(String[] args) {
        System.out.println("WeightUnit");
        System.out.println("1 KILOGRAM in GRAMS: " +
                WeightUnit.KILOGRAM.convertToBaseUnit(1.0));
        System.out.println("1000 GRAMS in KILOGRAMS: " +
                WeightUnit.KILOGRAM.convertFromBaseUnit(1000.0));
    }
}