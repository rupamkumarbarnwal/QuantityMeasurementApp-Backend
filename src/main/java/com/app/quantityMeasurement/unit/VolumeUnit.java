package com.app.quantityMeasurement.unit;

public enum VolumeUnit implements IMeasurable {

    MILLILITRE(1.0),
    LITRE(1000.0),
    GALLON(3785.41),
    CUBIC_CENTIMETER(1.0),
    CUBIC_METER(1000000.0),
    CUBIC_INCH(16.3871),
    CUBIC_FEET(28316.8);

    private final double conversionFactor;

    VolumeUnit(double conversionFactor) {
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
        return "VolumeUnit";
    }

    @Override
    public IMeasurable getUnitInstance(String unitName) {
        try {
            return VolumeUnit.valueOf(
                    unitName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown VolumeUnit: " + unitName);
        }
    }

    public static void main(String[] args) {
        System.out.println("VolumeUnit");
        System.out.println("1 LITRE in MILLILITRES: " +
                VolumeUnit.LITRE.convertToBaseUnit(1.0));
        System.out.println("1000 MILLILITRES in LITRES: " +
                VolumeUnit.LITRE.convertFromBaseUnit(1000.0));
    }
}