package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.QuantityMeasurementApp.Feet;

public class QuantityMeasurementAppTest {

    @Test
    public void testEquality_SameValue() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);

        assertTrue(feet1.equals(feet2), 
                "1.0 ft should be equal to 1.0 ft");
    }

    @Test
    public void testEquality_DifferentValue() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(2.0);

        assertFalse(feet1.equals(feet2), 
                "1.0 ft should not be equal to 2.0 ft");
    }

    @Test
    public void testEquality_NullComparison() {
        Feet feet = new Feet(1.0);

        assertFalse(feet.equals(null), 
                "Feet object should not be equal to null");
    }

    @Test
    public void testEquality_DifferentClass() {
        Feet feet = new Feet(1.0);
        String value = "1.0";

        assertFalse(feet.equals(value), 
                "Feet object should not be equal to different class object");
    }

    @Test
    public void testEquality_SameReference() {
        Feet feet = new Feet(1.0);

        assertTrue(feet.equals(feet), 
                "Object should be equal to itself (reflexive property)");
    }
}
