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
    @Test
    public void testEquality_YardToYard_SameValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(1.0, Length.LengthUnit.YARDS);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testEquality_YardToFeet_EquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(3.0, Length.LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testEquality_YardToInches_EquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(36.0, Length.LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }
    @Test
    public void testEquality_CentimeterToInches_EquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length l2 = new Length(0.393701, Length.LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    public void testEquality_CentimeterToFeet_NonEquivalentValue() {
        Length l1 = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);

        assertFalse(l1.equals(l2));
    }


        private static final double EPS = 1e-6;

        @Test
        public void testEquality_FeetToFeet() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(1.0, Length.LengthUnit.FEET);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testEquality_YardToFeet() {
            Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
            Length l2 = new Length(3.0, Length.LengthUnit.FEET);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testEquality_CentimeterToInch() {
            Length l1 = new Length(2.54, Length.LengthUnit.CENTIMETERS);
            Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testConversion_FeetToInches() {
            double result = Length.convert(1.0,
                    Length.LengthUnit.FEET,
                    Length.LengthUnit.INCHES);
            assertEquals(12.0, result, EPS);
        }

        @Test
        public void testConversion_RoundTrip() {
            double original = 5.0;

            double inches = Length.convert(original,
                    Length.LengthUnit.FEET,
                    Length.LengthUnit.INCHES);

            double back = Length.convert(inches,
                    Length.LengthUnit.INCHES,
                    Length.LengthUnit.FEET);

            assertEquals(original, back, EPS);
        }

        @Test
        public void testConversion_Negative() {
            double result = Length.convert(-1.0,
                    Length.LengthUnit.FEET,
                    Length.LengthUnit.INCHES);

            assertEquals(-12.0, result, EPS);
        }

        @Test
        public void testConversion_InvalidUnit() {
            assertThrows(IllegalArgumentException.class, () -> {
                Length.convert(1.0, null, Length.LengthUnit.FEET);
            });
        }

        @Test
        public void testConversion_NaN() {
            assertThrows(IllegalArgumentException.class, () -> {
                Length.convert(Double.NaN,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES);
            });
        }
    
    
    
}



