package com.app.quantityMeasurement.quantity;

import com.app.quantityMeasurement.unit.IMeasurable;
import com.app.quantityMeasurement.unit.TemperatureUnit;

import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U      unit;

    public Quantity(double value, U unit) {
        this.value = value;
        this.unit  = unit;
    }

    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (b == 0.0)
                throw new ArithmeticException(
                        "Cannot divide by zero");
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

    private void validateArithmeticOperands(
            Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException(
                    "Operand cannot be null");
        if (this.unit.getClass() !=
                other.unit.getClass())
            throw new IllegalArgumentException(
                    "Incompatible unit categories");
        if (!Double.isFinite(this.value))
            throw new IllegalArgumentException(
                    "Invalid numeric value in first quantity");
        if (!Double.isFinite(other.value))
            throw new IllegalArgumentException(
                    "Invalid numeric value in second quantity");
    }

    private void validateOperationSupport(
            ArithmeticOperation operation) {
        unit.validateOperationSupport(operation.name());
    }

    private double performArithmetic(
            Quantity<U> other,
            ArithmeticOperation operation) {
        validateOperationSupport(operation);
        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(
                other.value);
        return operation.compute(base1, base2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ||
                getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (this.unit.getClass() !=
                other.unit.getClass()) return false;

        double base1;
        double base2;

        if (this.unit instanceof TemperatureUnit) {
            base1 = ((TemperatureUnit) this.unit)
                    .convertTo(this.value,
                            TemperatureUnit.CELSIUS);
            base2 = ((TemperatureUnit) other.unit)
                    .convertTo((double) other.value,
                            TemperatureUnit.CELSIUS);
        } else {
            base1 = this.unit.convertToBaseUnit(this.value);
            base2 = other.unit.convertToBaseUnit(
                    (double) other.value);
        }

        return Math.abs(base1 - base2) < 1e-6;
    }

    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(value);
        return Double.hashCode(base);
    }

    public <T extends IMeasurable> Quantity<T> convertTo(
            T targetUnit) {
        if (unit instanceof TemperatureUnit &&
                targetUnit instanceof TemperatureUnit) {
            double converted =
                    ((TemperatureUnit) unit).convertTo(
                            value,
                            (TemperatureUnit) targetUnit);
            return new Quantity<>(converted, targetUnit);
        }
        double base      =
                unit.convertToBaseUnit(value);
        double converted =
                targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(converted, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        validateArithmeticOperands(other);
        double baseResult =
                performArithmetic(other,
                        ArithmeticOperation.ADD);
        double result =
                unit.convertFromBaseUnit(baseResult);
        return new Quantity<>(result, unit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        validateArithmeticOperands(other);
        double baseResult =
                performArithmetic(other,
                        ArithmeticOperation.SUBTRACT);
        double result =
                unit.convertFromBaseUnit(baseResult);
        return new Quantity<>(result, unit);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other);
        return performArithmetic(other,
                ArithmeticOperation.DIVIDE);
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }

    public static void main(String[] args) {
        System.out.println("Quantity");
    }
}
