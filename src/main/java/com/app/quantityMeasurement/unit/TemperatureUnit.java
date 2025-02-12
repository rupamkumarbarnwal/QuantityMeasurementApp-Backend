package com.app.quantityMeasurement.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(false,
            value -> value,
            value -> value),

    FAHRENHEIT(true,
            value -> (value - 32.0) * 5.0 / 9.0,
            value -> value * 9.0 / 5.0 + 32.0);

    private final boolean            isFahrenheit;
    private final Function<Double,
            Double>                  toCelsius;
    private final Function<Double,
            Double>                  fromCelsius;

    TemperatureUnit(boolean isFahrenheit,
                    Function<Double, Double> toCelsius,
                    Function<Double, Double> fromCelsius) {
        this.isFahrenheit = isFahrenheit;
        this.toCelsius    = toCelsius;
        this.fromCelsius  = fromCelsius;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toCelsius.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double value) {
        return fromCelsius.apply(value);
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType() {
        return "TemperatureUnit";
    }

    @Override
    public IMeasurable getUnitInstance(String unitName) {
        try {
            return TemperatureUnit.valueOf(
                    unitName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown TemperatureUnit: " + unitName);
        }
    }

    public double convertTo(double value,
                             TemperatureUnit targetUnit) {
        double celsius = this.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(celsius);
    }

    public boolean isFahrenheit() {
        return isFahrenheit;
    }

    public static void main(String[] args) {
        System.out.println("TemperatureUnit");
        System.out.println("100 CELSIUS in FAHRENHEIT: " +
                TemperatureUnit.CELSIUS.convertTo(
                        100.0, TemperatureUnit.FAHRENHEIT));
        System.out.println("212 FAHRENHEIT in CELSIUS: " +
                TemperatureUnit.FAHRENHEIT.convertTo(
                        212.0, TemperatureUnit.CELSIUS));
        System.out.println("Supports arithmetic: " +
                TemperatureUnit.CELSIUS.supportsArithmetic());
    }
}