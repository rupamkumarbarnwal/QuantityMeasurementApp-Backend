package com.app.quantityMeasurement.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;

public final class QuantityDTO {

    @NotNull(message =
            "Value must not be null")
    private final double value;

    @NotEmpty(message =
            "Unit name must not be empty")
    private final String unitName;

    @NotEmpty(message =
            "Measurement type must not be empty")
    @Pattern(
            regexp =
                    "LengthUnit|WeightUnit|" +
                            "VolumeUnit|TemperatureUnit",
            message =
                    "Measurement type must be one of: " +
                            "LengthUnit, WeightUnit, " +
                            "VolumeUnit, TemperatureUnit"
    )
    private final String measurementType;

    // ── Validation ────────────────────────────────

    @AssertTrue(message =
            "Unit must be valid for the " +
                    "specified measurement type")
    public boolean isValidUnit() {

        if (unitName == null ||
                measurementType == null) {
            return false;
        }

        try {
            switch (measurementType) {

                case "LengthUnit":
                    LengthUnit.valueOf(unitName);
                    return true;

                case "WeightUnit":
                    WeightUnit.valueOf(unitName);
                    return true;

                case "VolumeUnit":
                    VolumeUnit.valueOf(unitName);
                    return true;

                case "TemperatureUnit":
                    TemperatureUnit.valueOf(unitName);
                    return true;

                default:
                    return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // ── Inner Enums ───────────────────────────────

    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    public enum LengthUnit
            implements IMeasurableUnit {
        INCHES, FEET, YARD,
        CENTIMETER, METER,
        KILOMETER, MILE;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return "LengthUnit";
        }
    }

    public enum WeightUnit
            implements IMeasurableUnit {
        GRAM, KILOGRAM, TONNE,
        POUND, OUNCE;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return "WeightUnit";
        }
    }

    public enum VolumeUnit
            implements IMeasurableUnit {
        MILLILITRE, LITRE, GALLON,
        CUBIC_CENTIMETER, CUBIC_METER,
        CUBIC_INCH, CUBIC_FEET;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return "VolumeUnit";
        }
    }

    public enum TemperatureUnit
            implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return "TemperatureUnit";
        }
    }

    // ── Constructors ──────────────────────────────

    public QuantityDTO(double value,
                       IMeasurableUnit unit) {
        this.value           = value;
        this.unitName        = unit.getUnitName();
        this.measurementType =
                unit.getMeasurementType();
    }

    public QuantityDTO(double value,
                       String unitName,
                       String measurementType) {
        this.value           = value;
        this.unitName        = unitName;
        this.measurementType = measurementType;
    }

    // ── Getters ───────────────────────────────────

    public double getValue() {
        return value;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public String getUnit() {
        return unitName;
    }

    // ── equals ────────────────────────────────────

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ||
                getClass() != obj.getClass())
            return false;
        QuantityDTO other = (QuantityDTO) obj;
        return Double.compare(
                other.value, value) == 0
                && unitName.equals(other.unitName)
                && measurementType.equals(
                other.measurementType);
    }

    // ── hashCode ──────────────────────────────────

    @Override
    public int hashCode() {
        int result = Double.hashCode(value);
        result = 31 * result +
                unitName.hashCode();
        result = 31 * result +
                measurementType.hashCode();
        return result;
    }

    // ── toString ──────────────────────────────────

    @Override
    public String toString() {
        return value + " " + unitName;
    }
}