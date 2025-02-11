package quantityMeasurement;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import length.LengthUnit;
import temperature.TemperatureUnit;
import volume.VolumeUnit;
import weight.WeightUnit;

public class Quantity<U extends IMeasurable> {

    private double value;
    private U unit;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    @SuppressWarnings("unchecked")
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        if (!unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Incompatible units");

        if (this.unit instanceof TemperatureUnit) {
            TemperatureUnit srcTemp    = (TemperatureUnit) this.unit;
            TemperatureUnit targetTemp = (TemperatureUnit) targetUnit;
            double converted = srcTemp.convertTo(this.value, targetTemp);
            return new Quantity<>(converted, targetUnit);
        }

        double baseValue      = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
        return new Quantity<>(convertedValue, targetUnit);
    }


    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (b == 0.0) throw new ArithmeticException("Cannot divide by zero");
            return a / b;
        });

        private final DoubleBinaryOperator operation;

        ArithmeticOperation(DoubleBinaryOperator operation) {
            this.operation = operation;
        }

        public double compute(double a, double b) {
            return operation.applyAsDouble(a, b);
        }
    }


    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");
        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Incompatible unit categories");
        if (!Double.isFinite(this.value))
            throw new IllegalArgumentException("Invalid numeric value in this quantity");
        if (!Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value in other quantity");
        if (targetUnitRequired && targetUnit == null)
            throw new NullPointerException("Target unit cannot be null");
    }

    // UC14 CHANGE 2: performArithmetic validates operation support before executing
    private double performArithmetic(Quantity<U> other, U targetUnit, ArithmeticOperation operation) {
        this.unit.validateOperationSupport(operation.name());
        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);
        return operation.compute(baseValue1, baseValue2);
    }


    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double resultBase = performArithmetic(other, targetUnit, ArithmeticOperation.ADD);
        return new Quantity<>(targetUnit.convertFromBaseUnit(resultBase), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double resultBase = performArithmetic(other, targetUnit, ArithmeticOperation.SUBTRACT);
        return new Quantity<>(targetUnit.convertFromBaseUnit(resultBase), targetUnit);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        return performArithmetic(other, null, ArithmeticOperation.DIVIDE);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Quantity)) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (this.unit.getClass() != other.unit.getClass())
            return false;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < 1e-6;
    }

    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        return Objects.hash(baseValue, unit.getClass());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }

    public static void main(String[] args) {

        Quantity<LengthUnit> length1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> length2 = new Quantity<>(12.0, LengthUnit.INCHES);
        System.out.println("Length Equality: "       + length1.equals(length2));
        System.out.println("Length Addition: "       + length1.add(length2));
        System.out.println("Convert Feet to Yards: " + length1.convertTo(LengthUnit.YARDS));
        System.out.println("Subtract Length: "       + length1.subtract(length2));
        System.out.println("Divide Length: "         + length1.divide(length2));

        Quantity<WeightUnit> weight1 = new Quantity<>(1.0,    WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weight2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        System.out.println("Weight Equality: "     + weight1.equals(weight2));
        System.out.println("Weight Addition: "     + weight1.add(weight2));
        System.out.println("Convert KG to Pound: " + weight1.convertTo(WeightUnit.POUND));
        System.out.println("Subtract Weight: "     + weight1.subtract(weight2));
        System.out.println("Divide Weight: "       + weight1.divide(weight2));

        Quantity<VolumeUnit> volume1 = new Quantity<>(1.0,    VolumeUnit.LITRE);
        Quantity<VolumeUnit> volume2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        System.out.println("Volume Equality: "        + volume1.equals(volume2));
        System.out.println("Volume Addition: "        + volume1.add(volume2));
        System.out.println("Convert Litre to Gallon:" + volume1.convertTo(VolumeUnit.GALLON));
        System.out.println("Subtract Volume: "        + volume1.subtract(volume2));
        System.out.println("Divide Volume: "          + volume1.divide(volume2));
    }
}