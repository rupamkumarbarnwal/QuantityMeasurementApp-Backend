package quantityMeasurement;

import org.junit.jupiter.api.Test;

import length.Length;
import length.LengthUnit;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {
    private static final double EPSILON = 1e-6;

    @Test
    public void testFeetConversionFactor() {
        assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
    }

    @Test
    public void testInchesConversionFactor() {
        assertEquals(1.0 / 12.0, LengthUnit.INCHES.getConversionFactor(), EPSILON);
    }

    @Test
    public void testYardsConversionFactor() {
        assertEquals(3.0, LengthUnit.YARDS.getConversionFactor(), EPSILON);
    }

    @Test
    public void testCentimetersConversionFactor() {
        assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.getConversionFactor(), EPSILON);
    }
	@Test
	public void testEquality_FeetToFeet_SameValue() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(1.0, LengthUnit.FEET);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_InchToInch_SameValue() {
		Length l1 = new Length(1.0, LengthUnit.INCHES);
		Length l2 = new Length(1.0, LengthUnit.INCHES);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_FeetToInch_EquivalentValue() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_InchToFeet_EquivalentValue() {
		Length l1 = new Length(12.0, LengthUnit.INCHES);
		Length l2 = new Length(1.0, LengthUnit.FEET);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_FeetToFeet_DifferentValue() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(2.0, LengthUnit.FEET);

		assertFalse(l1.equals(l2));
	}

	@Test
	public void testEquality_InchToInch_DifferentValue() {
		Length l1 = new Length(1.0, LengthUnit.INCHES);
		Length l2 = new Length(2.0, LengthUnit.INCHES);

		assertFalse(l1.equals(l2));
	}

	@Test
	public void testEquality_SameReference() {
		Length l1 = new Length(1.0, LengthUnit.FEET);

		assertTrue(l1.equals(l1));
	}

	@Test
	public void testEquality_NullComparison() {
		Length l1 = new Length(1.0, LengthUnit.FEET);

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
		Length l1 = new Length(1.0, LengthUnit.YARDS);
		Length l2 = new Length(1.0, LengthUnit.YARDS);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_YardToFeet_EquivalentValue() {
		Length l1 = new Length(1.0, LengthUnit.YARDS);
		Length l2 = new Length(3.0, LengthUnit.FEET);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_YardToInches_EquivalentValue() {
		Length l1 = new Length(1.0, LengthUnit.YARDS);
		Length l2 = new Length(36.0, LengthUnit.INCHES);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_CentimeterToInches_EquivalentValue() {
		Length l1 = new Length(1.0, LengthUnit.CENTIMETERS);
		Length l2 = new Length(0.393701, LengthUnit.INCHES);

		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_CentimeterToFeet_NonEquivalentValue() {
		Length l1 = new Length(1.0, LengthUnit.CENTIMETERS);
		Length l2 = new Length(1.0, LengthUnit.FEET);

		assertFalse(l1.equals(l2));
	}

	private static final double EPS = 1e-6;

	@Test
	public void testEquality_FeetToFeet() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(1.0, LengthUnit.FEET);
		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_YardToFeet() {
		Length l1 = new Length(1.0, LengthUnit.YARDS);
		Length l2 = new Length(3.0, LengthUnit.FEET);
		assertTrue(l1.equals(l2));
	}

	@Test
	public void testEquality_CentimeterToInch() {
		Length l1 = new Length(2.54, LengthUnit.CENTIMETERS);
		Length l2 = new Length(1.0, LengthUnit.INCHES);
		assertTrue(l1.equals(l2));
	}

	@Test
	public void testConversion_FeetToInches() {
		double result = Length.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
		assertEquals(12.0, result, EPS);
	}

	@Test
	public void testConversion_RoundTrip() {
		double original = 5.0;

		double inches = Length.convert(original, LengthUnit.FEET, LengthUnit.INCHES);

		double back = Length.convert(inches, LengthUnit.INCHES, LengthUnit.FEET);

		assertEquals(original, back, EPS);
	}

	@Test
	public void testConversion_Negative() {
		double result = Length.convert(-1.0, LengthUnit.FEET, LengthUnit.INCHES);

		assertEquals(-12.0, result, EPS);
	}

	@Test
	public void testConversion_InvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			Length.convert(1.0, null, LengthUnit.FEET);
		});
	}

	@Test
	public void testConversion_NaN() {
		assertThrows(IllegalArgumentException.class, () -> {
			Length.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES);
		});
	}

	public void testAddition_FeetPlusInches() {

		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);

		Length result = l1.add(l2);

		assertEquals(2.0, result.getValue(), EPS);
	}

	@Test
	public void testAddition_Commutative() {

		Length a = new Length(1.0, LengthUnit.FEET);
		Length b = new Length(12.0, LengthUnit.INCHES);

		Length r1 = a.add(b);
		Length r2 = b.add(a);

		assertTrue(r1.equals(r2));
	}

	@Test
	public void testAddition_NullThrows() {

		Length l1 = new Length(1.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> {
			l1.add(null);
		});
	}
	@Test
	public void testAddition_WithExplicitTargetUnit_Yards() {

	    Length l1 = new Length(1.0, LengthUnit.FEET);
	    Length l2 = new Length(12.0, LengthUnit.INCHES);

	    Length result = l1.add(l2, LengthUnit.YARDS);

	    assertEquals(0.666666, result.getValue(), 1e-6);
	}

}
