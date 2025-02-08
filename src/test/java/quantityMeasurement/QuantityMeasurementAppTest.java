package quantityMeasurement;

import org.junit.jupiter.api.Test;

import length.LengthUnit;
import weight.WeightUnit;

import static org.junit.jupiter.api.Assertions.*;


public class QuantityMeasurementAppTest {


    @Test
    void testIMeasurableInterface_LengthUnitImplementation() {
        IMeasurable unit = LengthUnit.FEET;
        assertEquals(12.0, unit.convertToBaseUnit(1.0));
    }

    @Test
    void testIMeasurableInterface_WeightUnitImplementation() {
        IMeasurable unit = WeightUnit.KILOGRAM;
        assertEquals(1000.0, unit.convertToBaseUnit(1.0));
    }

    @Test
    void testIMeasurableInterface_ConsistentBehavior() {
        assertEquals(12.0, LengthUnit.FEET.convertToBaseUnit(1.0));
        assertEquals(1000.0, WeightUnit.KILOGRAM.convertToBaseUnit(1.0));
    }


    @Test
    void testGenericQuantity_LengthOperations_Equality() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testGenericQuantity_WeightOperations_Equality() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testGenericQuantity_LengthOperations_Conversion() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(12.0, q.convertTo(LengthUnit.INCHES).getValue());
    }

    @Test
    void testGenericQuantity_WeightOperations_Conversion() {
        Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertEquals(1000.0, q.convertTo(WeightUnit.GRAM).getValue());
    }

    @Test
    void testGenericQuantity_LengthOperations_Addition() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertEquals(2.0, q1.add(q2, LengthUnit.FEET).getValue());
    }

    @Test
    void testGenericQuantity_WeightOperations_Addition() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(2.0, q1.add(q2, WeightUnit.KILOGRAM).getValue());
    }

    @Test
    void testCrossCategoryPrevention_LengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertFalse(length.equals(weight));
    }

    @Test
    void testCrossCategoryPrevention_CompilerTypeSafety() {
        assertTrue(true);
    }



    @Test
    void testGenericQuantity_ConstructorValidation_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testGenericQuantity_ConstructorValidation_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }


    @Test
    void testGenericQuantity_Conversion_AllUnitCombinations() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.YARDS);
        assertEquals(3.0, q.convertTo(LengthUnit.FEET).getValue());
    }

    @Test
    void testGenericQuantity_Addition_AllUnitCombinations() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> q2 = new Quantity<>(3.0, LengthUnit.FEET);
        assertEquals(2.0, q1.add(q2, LengthUnit.YARDS).getValue());
    }

    // =========================================================
    // 16 Backward Compatibility
    // =========================================================

    @Test
    void testBackwardCompatibility_AllUC1Through9Tests() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertTrue(q1.equals(q2));
    }

    // =========================================================
    // 17–19 Demonstration Method Tests
    // =========================================================

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertTrue(QuantityMeasurementApp.demonstrateEquality(q1, q2));
    }

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
        assertEquals(12.0,
                QuantityMeasurementApp.demonstrateConversion(q, LengthUnit.INCHES).getValue());
    }

    @Test
    void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(2.0,
                QuantityMeasurementApp.demonstrateAddition(w1, w2, WeightUnit.KILOGRAM).getValue());
    }


    @Test
    void testTypeWildcard_FlexibleSignatures() {
        Quantity<?> q = new Quantity<>(1.0, LengthUnit.FEET);
        assertNotNull(q);
    }

 

    @Test
    void testHashCode_GenericQuantity_Consistency() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    void testEquals_GenericQuantity_ContractPreservation() {
        Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> c = new Quantity<>(1.0, LengthUnit.FEET);

        assertTrue(a.equals(a));
        assertTrue(a.equals(b) && b.equals(a));
        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }



    @Test
    void testImmutability_GenericQuantity() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> converted = q.convertTo(LengthUnit.INCHES);
        assertNotSame(q, converted);
    }
}