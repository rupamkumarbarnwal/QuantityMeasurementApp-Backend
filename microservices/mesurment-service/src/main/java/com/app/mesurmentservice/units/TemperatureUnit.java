package com.app.mesurmentservice.units;


import com.app.mesurmentservice.IMeasurable;
import com.app.mesurmentservice.SupportsArithmetic;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    SupportsArithmetic supportsArithmetic = () -> false;

    public double toBaseUnit(double value) {

        switch (this) {
            case CELSIUS:
                return value + 273.15;
            case FAHRENHEIT:
                return (value - 32) * 5 / 9 + 273.15;
            case KELVIN:
                return value;
        }
        return value;
    }

    public double fromBaseUnit(double value) {

        switch (this) {
            case CELSIUS:
                return value - 273.15;
            case FAHRENHEIT:
                return (value - 273.15) * 9 / 5 + 32;
            case KELVIN:
                return value;
        }
        return value;
    }
    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }
    @Override
    public void validateOperationSupport(String operation) {

        if (operation.equalsIgnoreCase("addition") ||
                operation.equalsIgnoreCase("subtraction") ||
                operation.equalsIgnoreCase("division")) {

            throw new UnsupportedOperationException(
                    "Temperature does not support " + operation
            );
        }

        // compare & convert → allowed ✅
    }
}