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
	    public void testEquality_NullUnit(){
	        assertThrows(IllegalArgumentException.class, () -> {
	            new Length(1.0, null);
	        });
	    }
	    @Test
	    public void testFeetEquality() {
	        Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
	        Length feet2 = new Length(1.0, Length.LengthUnit.FEET);

	        assertTrue(feet1.equals(feet2));
	    }
	    public void testInchesEquality() {
	        Length inch1 = new Length(1.0, Length.LengthUnit.INCHES);
	        Length inch2 = new Length(1.0, Length.LengthUnit.INCHES);

	        assertTrue(inch1.equals(inch2));
	    }
	    public void testFeetInchesComparison() {
	        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
	        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);

	        assertTrue(length1.equals(length2));
	    }
	    public void testFeetInequality(){
	        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
	        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);

	        assertFalse(length1.equals(length2));
	    }
	    public void testInchesInequality(){
	        Length length1 = new Length(1.0, Length.LengthUnit.INCHES);
	        Length length2 = new Length(2.0, Length.LengthUnit.INCHES);

	        assertFalse(length1.equals(length2));
	    }
	    public void testCrossUnitInequality(){
	        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
	        Length length2 = new Length(13.0, Length.LengthUnit.INCHES);

	        assertFalse(length1.equals(length2));
	    }
	    public void testMultipleComparisons() {
	        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
	        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);
	        Length length3 = new Length(1.0, Length.LengthUnit.FEET);

	        assertTrue(length1.equals(length2));
	        assertTrue(length1.equals(length3));
	        assertTrue(length2.equals(length3));
	    }
}
