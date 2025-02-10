package quantityMeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import length.LengthUnit;
import weight.WeightUnit;
import volume.VolumeUnit;

public class QuantityMeasurementAppTest {

	private static final double EPSILON = 1e-6;

	@Test
	void testSubtraction_SameUnit_FeetMinusFeet() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(2.0, LengthUnit.FEET));

		assertEquals(3.0, result.getValue());
	}

	@Test
	void testSubtraction_SameUnit_LitreMinusLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(2.0, VolumeUnit.LITRE));

		assertEquals(3.0, result.getValue());
	}

	@Test
	void testSubtraction_CrossUnit_FeetMinusInches() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(24.0, LengthUnit.INCHES));

		assertEquals(3.0, result.getValue());
	}

	@Test
	void testSubtraction_CrossUnit_InchesMinusFeet() {
		Quantity<LengthUnit> result = new Quantity<>(24.0, LengthUnit.INCHES)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET));

		assertEquals(-36.0, result.getValue());
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Feet() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(24.0, LengthUnit.INCHES), LengthUnit.FEET);

		assertEquals(3.0, result.getValue());
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Inches() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(24.0, LengthUnit.INCHES), LengthUnit.INCHES);

		assertEquals(36.0, result.getValue());
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Millilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(2000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);

		assertEquals(3000.0, result.getValue());
	}

	@Test
	void testSubtraction_ResultingInNegative() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(6000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);

		assertEquals(-1.0, result.getValue());

	}

	@Test
	void testSubtraction_ResultingInZero() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(5000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);

		assertEquals(0.0, result.getValue());
	}

	@Test
	void testSubtraction_WithZeroOperand() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(0.0, LengthUnit.INCHES), LengthUnit.FEET);
		assertEquals(5.0, result.getValue());
	}

	@Test
	void testSubtraction_WithNegativeValues() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(-24.0, LengthUnit.INCHES), LengthUnit.FEET);
		assertEquals(7.0, result.getValue());
	}

	@Test
	void testSubtraction_NonCommutative() {
		Quantity<LengthUnit> result1 = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(24.0, LengthUnit.INCHES), LengthUnit.FEET);
		Quantity<LengthUnit> result2 = new Quantity<>(24.0, LengthUnit.INCHES)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET), LengthUnit.FEET);
		assertEquals(3.0, result1.getValue());
		assertEquals(-3.0, result2.getValue());
	}

	@Test
	void testSubtraction_WithLargeValues() {
		Quantity<VolumeUnit> result = new Quantity<>(1e6, VolumeUnit.LITRE)
				.subtract(new Quantity<>(5e5, VolumeUnit.LITRE), VolumeUnit.LITRE);
		assertEquals(5e5, result.getValue());
	}

	@Test
	void testSubtraction_WithSmallValues() {
		Quantity<VolumeUnit> result = new Quantity<>(0.001, VolumeUnit.LITRE)
				.subtract(new Quantity<>(0.0005, VolumeUnit.LITRE), VolumeUnit.LITRE);
		assertEquals(0.0005, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_NullOperand() {
		assertThrows(IllegalArgumentException.class,
				() -> new Quantity<>(5.0, LengthUnit.FEET).subtract(null, LengthUnit.FEET));
	}

	@Test
	void testSubtraction_NullTargetUnit() {
		assertThrows(NullPointerException.class,
				() -> new Quantity<>(5.0, LengthUnit.FEET).subtract(new Quantity<>(24.0, LengthUnit.INCHES), null));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testSubtraction_CrossCategory() {

		Quantity<LengthUnit> lengthQty = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity weightQty = new Quantity(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class,
				() -> lengthQty.subtract((Quantity<LengthUnit>) weightQty, LengthUnit.FEET));
	}

	@Test
	void testSubtraction_AllMeasurementCategories() {
		Quantity<LengthUnit> lengthResult = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET));
		assertEquals(5.0, lengthResult.getValue());

		Quantity<WeightUnit> weightResult = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM));
		assertEquals(5.0, weightResult.getValue());

		Quantity<VolumeUnit> volumeResult = new Quantity<>(10.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(5.0, VolumeUnit.LITRE));
		assertEquals(5.0, volumeResult.getValue());
	}

	@Test
	void testSubtraction_SameUnit_FeetMinusFeet_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET));
		assertEquals(5.0, result.getValue());
	}

	@Test
	void testSubtraction_SameUnit_LitreMinusLitre_TaskSpec() {

		Quantity<VolumeUnit> result = new Quantity<>(10.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(3.0, VolumeUnit.LITRE));
		assertEquals(7.0, result.getValue());
	}

	@Test
	void testSubtraction_CrossUnit_FeetMinusInches_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCHES));
		assertEquals(9.5, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_CrossUnit_InchesMinusFeet_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(120.0, LengthUnit.INCHES)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET));
		assertEquals(60.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Feet_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.FEET);
		assertEquals(9.5, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Inches_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
		assertEquals(114.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_ExplicitTargetUnit_Millilitre_TaskSpec() {

		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.subtract(new Quantity<>(2.0, VolumeUnit.LITRE), VolumeUnit.MILLILITRE);
		assertEquals(3000.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_ResultingInNegative_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(-5.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_ResultingInZero_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(120.0, LengthUnit.INCHES));
		assertEquals(0.0, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_NonCommutative_TaskSpec() {

		Quantity<LengthUnit> result1 = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET));
		Quantity<LengthUnit> result2 = new Quantity<>(5.0, LengthUnit.FEET)
				.subtract(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(5.0, result1.getValue(), EPSILON);
		assertEquals(-5.0, result2.getValue(), EPSILON);
		assertNotEquals(result1.getValue(), result2.getValue());
	}

	@Test
	void testSubtraction_WithLargeValues_TaskSpec() {

		Quantity<WeightUnit> result = new Quantity<>(1e6, WeightUnit.KILOGRAM)
				.subtract(new Quantity<>(5e5, WeightUnit.KILOGRAM));
		assertEquals(5e5, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_WithSmallValues_TaskSpec() {

		Quantity<LengthUnit> result = new Quantity<>(0.001, LengthUnit.FEET)
				.subtract(new Quantity<>(0.0005, LengthUnit.FEET));
		assertEquals(0.0005, result.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_ChainedOperations() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(2.0, LengthUnit.FEET)).subtract(new Quantity<>(1.0, LengthUnit.FEET));
		assertEquals(7.0, result.getValue());
	}

	@Test
	void testDivision_SameUnit_FeetDividedByFeet() {
		double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(5.0, result);
	}

	@Test
	void testDivision_SameUnit_LitreDividedByLitre() {
		double result = new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(5.0, VolumeUnit.LITRE));
		assertEquals(2.0, result);
	}

	@Test
	void testDivision_CrossUnit_FeetDividedByInches() {
		double result = new Quantity<>(24.0, LengthUnit.INCHES).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(1.0, result);
	}

	@Test
	void testDivision_CrossUnit_KilogramDividedByGram() {
		double result = new Quantity<>(2.0, WeightUnit.KILOGRAM).divide(new Quantity<>(2000.0, WeightUnit.GRAM));
		assertEquals(1.0, result);
	}

	@Test
	void testDivision_RatioGreaterThanOne() {
		double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(5.0, result);
	}

	@Test
	void testDivision_RatioLessThanOne() {
		double result = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(0.5, result);
	}

	@Test
	void testDivision_RatioEqualToOne() {
		double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(1.0, result);
	}

	@Test
	void testDivision_NonCommutative() {
		double result1 = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(5.0, LengthUnit.FEET));
		double result2 = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(2.0, result1);
		assertEquals(0.5, result2);
		assertNotEquals(result1, result2);
	}

	@Test
	void testDivision_ByZero() {
		assertThrows(ArithmeticException.class,
				() -> new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(0.0, LengthUnit.FEET)));
	}

	@Test
	void testDivision_WithLargeRatio() {
		double result = new Quantity<>(1e6, WeightUnit.KILOGRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));
		assertEquals(1e6, result);
	}

	@Test
	void testDivision_WithSmallRatio() {
		double result = new Quantity<>(1.0, WeightUnit.KILOGRAM).divide(new Quantity<>(1e6, WeightUnit.KILOGRAM));
		assertEquals(1e-6, result, EPSILON);
	}

	@Test
	void testDivision_NullOperand() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(10.0, LengthUnit.FEET).divide(null));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testDivision_CrossCategory() {
		// Using raw type to bypass compile-time type checking
		// to test runtime category validation
		Quantity<LengthUnit> lengthQty = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity weightQty = new Quantity(5.0, WeightUnit.KILOGRAM);

		assertThrows(IllegalArgumentException.class, () -> lengthQty.divide((Quantity<LengthUnit>) weightQty));
	}

	@Test
	void testDivision_AllMeasurementCategories() {

		double lengthResult = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(5.0, lengthResult);

		double weightResult = new Quantity<>(10.0, WeightUnit.KILOGRAM)
				.divide(new Quantity<>(2.0, WeightUnit.KILOGRAM));
		assertEquals(5.0, weightResult);

		double volumeResult = new Quantity<>(10.0, VolumeUnit.LITRE).divide(new Quantity<>(2.0, VolumeUnit.LITRE));
		assertEquals(5.0, volumeResult);
	}

	@Test
	void testDivision_Associativity() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
		Quantity<LengthUnit> c = new Quantity<>(2.0, LengthUnit.FEET);

		double ab = a.divide(b);
		double result1 = ab / c.getValue();

		double bc = b.divide(c);
		double result2 = a.getValue() / bc;

		assertNotEquals(result1, result2, EPSILON);
	}

	@Test
	void testSubtractionAndDivision_Integration() {

		Quantity<LengthUnit> subtractResult = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(2.0, LengthUnit.FEET));

		double divisionResult = subtractResult.divide(new Quantity<>(2.0, LengthUnit.FEET));

		assertEquals(4.0, divisionResult);
	}

	@Test
	void testSubtractionAddition_Inverse() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);

		Quantity<LengthUnit> added = a.add(b);
		Quantity<LengthUnit> subtracted = added.subtract(b);

		assertEquals(a.getValue(), subtracted.getValue(), EPSILON);
	}

	@Test
	void testSubtraction_Immutability() {
		Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> originalCopy = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> subtractor = new Quantity<>(3.0, LengthUnit.FEET);

		original.subtract(subtractor);

		assertEquals(originalCopy.getValue(), original.getValue());
		assertEquals(originalCopy.getUnit(), original.getUnit());
	}

	@Test
	void testDivision_Immutability() {
		Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> originalCopy = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> divisor = new Quantity<>(2.0, LengthUnit.FEET);

		original.divide(divisor);

		assertEquals(originalCopy.getValue(), original.getValue());
		assertEquals(originalCopy.getUnit(), original.getUnit());
	}

	@Test
	void testSubtraction_PrecisionAndRounding() {

		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCHES));

		assertEquals(9.5, result.getValue(), EPSILON);
	}

	@Test
	void testDivision_PrecisionHandling() {

		double result = new Quantity<>(1.0, LengthUnit.FEET).divide(new Quantity<>(3.0, LengthUnit.FEET));

		assertEquals(0.333333333, result, EPSILON);
	}

}
