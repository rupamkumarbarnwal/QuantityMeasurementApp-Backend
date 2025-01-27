package quantityMeasurement;



import org.junit.jupiter.api.Test;

import length.Length;

import static org.junit.jupiter.api.Assertions.*;



public class QuantityMeasurementAppTest {

    @Test
    public void testEquality_FeetToFeet_SameValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testEquality_InchToInch_SameValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testEquality_FeetToInch_EquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testEquality_InchToFeet_EquivalentValue() {
        Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testEquality_FeetToFeet_DifferentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(2.0, Length.LengthUnit.FEET);

        assertFalse(l1.equals(l2));
    }

    @Test
    public void testEquality_InchToInch_DifferentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(2.0, Length.LengthUnit.INCHES);

        assertFalse(l1.equals(l2));
    }

    @Test
    public void testEquality_SameReference() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertTrue(l1.equals(l1));
    }

    @Test
    public void testEquality_NullComparison() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }

    @Test
    public void testEquality_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(1.0, null);
        });
    }
}

//public class QuantityMeasurementAppTest {
//	
//	// Feet test cases
//
//    @Test
//    public void testFeetEquality_SameValue() {
//        Feet feet1 = new Feet(1.0);
//        Feet feet2 = new Feet(1.0);
//
//        assertTrue(feet1.equals(feet2));
//    }
//    
//    @Test
//    public void testFeetEquality_DifferentValue() {
//        Feet feet1 = new Feet(1.0);
//        Feet feet2 = new Feet(2.0);
//
//        assertFalse(feet1.equals(feet2));
//    }
//    
//    @Test
//    public void testFeetEquality_NullComparison() {
//        Feet feet1 = new Feet(1.0);
//
//        assertFalse(feet1.equals(null));
//    }
//    
//    @Test
//    public void testFeetEquality_DifferentClass() {
//        Feet feet1 = new Feet(1.0);
//        String other = "1.0";
//
//        assertFalse(feet1.equals(other));
//    }
//    
//    @Test
//    public void testFeetEquality_SameReference() {
//        Feet feet1 = new Feet(1.0);
//
//        assertTrue(feet1.equals(feet1));
//    }
//    
//    
//    // Inches test cases
//    @Test
//    public void testInchessEquality_SameValue() {
//        Inches i1 = new Inches(1.0);
//        Inches i2 = new Inches(1.0);
//
//        assertTrue(i1.equals(i2));
//    }
//
//    @Test
//    public void testInchesEquality_DifferentValue() {
//        Inches inch1 = new Inches(1.0);
//        Inches inch2 = new Inches(2.0);
//
//        assertFalse(inch1.equals(inch2));
//    }
//
//
//    @Test
//    public void testInchesEquality_NullComparison() {
//        Inches i1 = new Inches(1.0);
//
//        assertFalse(i1.equals(null));
//    }
//
//    @Test
//    public void testInchesEquality_DifferentClass() {
//        Inches i1 = new Inches(1.0);
//        Integer obj = 1;
//
//        assertFalse(i1.equals(obj));
//    }
//
//    @Test
//    public void testInchesEquality_SameReference() {
//        Inches i1 = new Inches(1.0);
//
//        assertTrue(i1.equals(i1));
//    }
