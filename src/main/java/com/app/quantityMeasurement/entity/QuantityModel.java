package com.app.quantityMeasurement.entity;

import com.app.quantityMeasurement.unit.IMeasurable;

public class QuantityModel<U extends IMeasurable> {

    private double value;
    private U      unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit  = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public U getUnit() {
        return unit;
    }

    public void setUnit(U unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ||
                getClass() != obj.getClass()) return false;
        QuantityModel<?> other = (QuantityModel<?>) obj;
        return Double.compare(other.value, value) == 0
                && unit.equals(other.unit);
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(value);
        result = 31 * result + unit.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }

    public static void main(String[] args) {
        System.out.println("QuantityModel");
    }
}