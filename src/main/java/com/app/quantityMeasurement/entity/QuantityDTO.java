package com.app.quantityMeasurement.entity;

public final class QuantityDTO {

    private final double value;
    private final String unitName;
    private final String measurementType;

    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        INCHES, FEET, YARD, CENTIMETER, METER, KILOMETER, MILE;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return "LengthUnit";
        }
    }

    public enum WeightUnit implements IMeasurableUnit {
        GRAM, KILOGRAM, TONNE, POUND, OUNCE;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return "WeightUnit";
        }
    }

    public enum VolumeUnit implements IMeasurableUnit {
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

    public enum TemperatureUnit implements IMeasurableUnit {
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

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value           = value;
        this.unitName        = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public QuantityDTO(double value,
                       String unitName,
                       String measurementType) {
        this.value           = value;
        this.unitName        = unitName;
        this.measurementType = measurementType;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ||
                getClass() != obj.getClass()) return false;
        QuantityDTO other = (QuantityDTO) obj;
        return Double.compare(other.value, value) == 0
                && unitName.equals(other.unitName)
                && measurementType.equals(other.measurementType);
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(value);
        result = 31 * result + unitName.hashCode();
        result = 31 * result + measurementType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return value + " " + unitName;
    }

    public static void main(String[] args) {
        System.out.println("QuantityDTO");
    }
}