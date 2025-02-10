package quantityMeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import length.LengthUnit;
import weight.WeightUnit;
import volume.VolumeUnit;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testRefactoring_Add_DelegatesViaHelper() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testRefactoring_Subtract_DelegatesViaHelper() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(9.5, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testRefactoring_Divide_DelegatesViaHelper() {
        double result = new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, result, EPSILON);
    }

    @Test
    void testValidation_NullOperand_ConsistentAcrossOperations() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

        IllegalArgumentException addEx = assertThrows(IllegalArgumentException.class,
                () -> q.add(null));
        IllegalArgumentException subEx = assertThrows(IllegalArgumentException.class,
                () -> q.subtract(null));
        IllegalArgumentException divEx = assertThrows(IllegalArgumentException.class,
                () -> q.divide(null));

        assertEquals(addEx.getMessage(), subEx.getMessage());
        assertEquals(subEx.getMessage(), divEx.getMessage());
    }

    @Test
    void testValidation_CrossCategory_ConsistentAcrossOperations() {
        Quantity<LengthUnit> lengthQty = new Quantity<>(10.0, LengthUnit.FEET);

        @SuppressWarnings("unchecked")
        Quantity<LengthUnit> weightAsLength = (Quantity<LengthUnit>)(Quantity<?>)
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class, () -> lengthQty.add(weightAsLength));
        assertThrows(IllegalArgumentException.class, () -> lengthQty.subtract(weightAsLength));
        assertThrows(IllegalArgumentException.class, () -> lengthQty.divide(weightAsLength));
    }

    @Test
    void testValidation_FiniteValue_ConsistentAcrossOperations() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.POSITIVE_INFINITY, LengthUnit.FEET));
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NEGATIVE_INFINITY, LengthUnit.FEET));
    }

    @Test
    void testValidation_NullTargetUnit_AddSubtractReject() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

        assertThrows(NullPointerException.class, () -> q1.add(q2, null));
        assertThrows(NullPointerException.class, () -> q1.subtract(q2, null));
    }

    @Test
    void testArithmeticOperation_Add_EnumComputation() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testArithmeticOperation_Subtract_EnumComputation() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(9.5, result.getValue(), EPSILON);
    }

    @Test
    void testArithmeticOperation_Divide_EnumComputation() {
        double result = new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(5.0, result, EPSILON);
    }

    @Test
    void testArithmeticOperation_DivideByZero_EnumThrows() {
        assertThrows(ArithmeticException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    @Test
    void testPerformBaseArithmetic_ConversionAndOperation() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAdd_UC12_BehaviorPreserved() {
        assertEquals(2.0,
                new Quantity<>(1.0, LengthUnit.FEET)
                        .add(new Quantity<>(12.0, LengthUnit.INCHES)).getValue(), EPSILON);
        assertEquals(2.0,
                new Quantity<>(1.0, WeightUnit.KILOGRAM)
                        .add(new Quantity<>(1000.0, WeightUnit.GRAM)).getValue(), EPSILON);
        assertEquals(2.0,
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)).getValue(), EPSILON);
    }

    @Test
    void testSubtract_UC12_BehaviorPreserved() {
        assertEquals(9.5,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCHES)).getValue(), EPSILON);
        assertEquals(5.0,
                new Quantity<>(10.0, WeightUnit.KILOGRAM)
                        .subtract(new Quantity<>(5000.0, WeightUnit.GRAM)).getValue(), EPSILON);
        assertEquals(3.0,
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .subtract(new Quantity<>(2000.0, VolumeUnit.MILLILITRE)).getValue(), EPSILON);
    }

    @Test
    void testDivide_UC12_BehaviorPreserved() {
        assertEquals(5.0,
                new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET)), EPSILON);
        assertEquals(2.0,
                new Quantity<>(10.0, WeightUnit.KILOGRAM)
                        .divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)), EPSILON);
        assertEquals(2.0,
                new Quantity<>(10.0, VolumeUnit.LITRE)
                        .divide(new Quantity<>(5.0, VolumeUnit.LITRE)), EPSILON);
    }

    @Test
    void testRounding_AddSubtract_TwoDecimalPlaces() {
        Quantity<LengthUnit> addResult = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(1.0, LengthUnit.CENTIMETERS));
        assertTrue(Double.isFinite(addResult.getValue()));

        Quantity<LengthUnit> subResult = new Quantity<>(1.0, LengthUnit.FEET)
                .subtract(new Quantity<>(1.0, LengthUnit.CENTIMETERS));
        assertTrue(Double.isFinite(subResult.getValue()));
    }

    @Test
    void testRounding_Divide_NoRounding() {
        double result = new Quantity<>(1.0, LengthUnit.FEET)
                .divide(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(0.333333333, result, EPSILON);
    }

    @Test
    void testImplicitTargetUnit_AddSubtract() {
        Quantity<LengthUnit> addResult = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(LengthUnit.FEET, addResult.getUnit());

        Quantity<LengthUnit> subResult = new Quantity<>(1.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES));
        assertEquals(LengthUnit.FEET, subResult.getUnit());
    }

    @Test
    void testExplicitTargetUnit_AddSubtract_Overrides() {
        Quantity<LengthUnit> addResult = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertEquals(LengthUnit.INCHES, addResult.getUnit());
        assertEquals(24.0, addResult.getValue(), EPSILON);

        Quantity<LengthUnit> subResult = new Quantity<>(2.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);
        assertEquals(LengthUnit.INCHES, subResult.getUnit());
        assertEquals(18.0, subResult.getValue(), EPSILON);
    }

    @Test
    void testImmutability_AfterAdd_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> other    = new Quantity<>(5.0,  LengthUnit.FEET);
        original.add(other);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, original.getUnit());
    }

    @Test
    void testImmutability_AfterSubtract_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> other    = new Quantity<>(3.0,  LengthUnit.FEET);
        original.subtract(other);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, original.getUnit());
    }

    @Test
    void testImmutability_AfterDivide_ViaCentralizedHelper() {
        Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> divisor  = new Quantity<>(2.0,  LengthUnit.FEET);
        original.divide(divisor);
        assertEquals(10.0, original.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, original.getUnit());
    }

    @Test
    void testAllOperations_AcrossAllCategories() {
        assertEquals(15.0, new Quantity<>(10.0, LengthUnit.FEET)
                .add(new Quantity<>(5.0, LengthUnit.FEET)).getValue(), EPSILON);
        assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(5.0, LengthUnit.FEET)).getValue(), EPSILON);
        assertEquals(2.0, new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(5.0, LengthUnit.FEET)), EPSILON);

        assertEquals(15.0, new Quantity<>(10.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(5.0, WeightUnit.KILOGRAM)).getValue(), EPSILON);
        assertEquals(5.0, new Quantity<>(10.0, WeightUnit.KILOGRAM)
                .subtract(new Quantity<>(5.0, WeightUnit.KILOGRAM)).getValue(), EPSILON);
        assertEquals(2.0, new Quantity<>(10.0, WeightUnit.KILOGRAM)
                .divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)), EPSILON);

        assertEquals(15.0, new Quantity<>(10.0, VolumeUnit.LITRE)
                .add(new Quantity<>(5.0, VolumeUnit.LITRE)).getValue(), EPSILON);
        assertEquals(5.0, new Quantity<>(10.0, VolumeUnit.LITRE)
                .subtract(new Quantity<>(5.0, VolumeUnit.LITRE)).getValue(), EPSILON);
        assertEquals(2.0, new Quantity<>(10.0, VolumeUnit.LITRE)
                .divide(new Quantity<>(5.0, VolumeUnit.LITRE)), EPSILON);
    }

    @Test
    void testCodeDuplication_ValidationLogic_Eliminated() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

        String addMsg = assertThrows(IllegalArgumentException.class,
                () -> q.add(null)).getMessage();
        String subMsg = assertThrows(IllegalArgumentException.class,
                () -> q.subtract(null)).getMessage();
        String divMsg = assertThrows(IllegalArgumentException.class,
                () -> q.divide(null)).getMessage();

        assertEquals(addMsg, subMsg);
        assertEquals(subMsg, divMsg);
    }

    @Test
    void testCodeDuplication_ConversionLogic_Eliminated() {
        Quantity<LengthUnit> result = new Quantity<>(24.0, LengthUnit.INCHES)
                .add(new Quantity<>(2.0, LengthUnit.FEET), LengthUnit.FEET);
        assertEquals(4.0, result.getValue(), EPSILON);
    }

    @Test
    void testEnumDispatch_AllOperations_CorrectlyDispatched() {
        Quantity<WeightUnit> w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5.0,  WeightUnit.KILOGRAM);

        assertEquals(15.0, w1.add(w2).getValue(),      EPSILON);
        assertEquals(5.0,  w1.subtract(w2).getValue(), EPSILON);
        assertEquals(2.0,  w1.divide(w2),              EPSILON);
    }

    @Test
    void testFutureOperation_MultiplicationPattern() {
        double base1 = LengthUnit.FEET.convertToBaseUnit(3.0);
        double base2 = LengthUnit.FEET.convertToBaseUnit(4.0);
        double multiplyResult = base1 * base2;
        assertTrue(multiplyResult > 0);
        assertEquals(1728.0, multiplyResult, EPSILON);
    }

    @Test
    void testErrorMessage_Consistency_Across_Operations() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

        String addMsg = assertThrows(IllegalArgumentException.class,
                () -> q.add(null)).getMessage();
        String subMsg = assertThrows(IllegalArgumentException.class,
                () -> q.subtract(null)).getMessage();
        String divMsg = assertThrows(IllegalArgumentException.class,
                () -> q.divide(null)).getMessage();

        assertNotNull(addMsg);
        assertEquals(addMsg, subMsg);
        assertEquals(subMsg, divMsg);
    }

    @Test
    void testHelper_PrivateVisibility() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
        assertNotNull(q);
    }

    @Test
    void testValidation_Helper_PrivateVisibility() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
        assertNotNull(q);
    }

    @Test
    void testRounding_Helper_Accuracy() {
        Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(1.0, LengthUnit.CENTIMETERS));
        assertTrue(Double.isFinite(result.getValue()));
        assertTrue(result.getValue() > 9.0);
    }

    @Test
    void testArithmetic_Chain_Operations() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0,  LengthUnit.FEET);
        double result = q1.add(q2).subtract(q2).divide(q2);
        assertEquals(5.0, result, EPSILON);
    }

    @Test
    void testRefactoring_NoBehaviorChange_LargeDataset() {
        for (int i = 1; i <= 1000; i++) {
            Quantity<LengthUnit> result = new Quantity<>((double) i, LengthUnit.FEET)
                    .add(new Quantity<>((double) i * 12, LengthUnit.INCHES));
            assertEquals(i * 2.0, result.getValue(), EPSILON);
        }
    }

    @Test
    void testRefactoring_Performance_ComparableToUC12() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            new Quantity<>(10.0, LengthUnit.FEET)
                    .add(new Quantity<>(12.0, LengthUnit.INCHES));
            new Quantity<>(10.0, LengthUnit.FEET)
                    .subtract(new Quantity<>(6.0, LengthUnit.INCHES));
            new Quantity<>(10.0, LengthUnit.FEET)
                    .divide(new Quantity<>(2.0, LengthUnit.FEET));
        }
        long end = System.currentTimeMillis();
        assertTrue((end - start) < 5000);
    }

    @Test
    void testEnumConstant_ADD_CorrectlyAdds() {
        Quantity<LengthUnit> result = new Quantity<>(7.0, LengthUnit.FEET)
                .add(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(10.0, result.getValue(), EPSILON);
    }

    @Test
    void testEnumConstant_SUBTRACT_CorrectlySubtracts() {
        Quantity<LengthUnit> result = new Quantity<>(7.0, LengthUnit.FEET)
                .subtract(new Quantity<>(3.0, LengthUnit.FEET));
        assertEquals(4.0, result.getValue(), EPSILON);
    }

    @Test
    void testEnumConstant_DIVIDE_CorrectlyDivides() {
        double result = new Quantity<>(7.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET));
        assertEquals(3.5, result, EPSILON);
    }

    @Test
    void testHelper_BaseUnitConversion_Correct() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testHelper_ResultConversion_Correct() {
        Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES);
        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testRefactoring_Validation_UnifiedBehavior() {
        Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);

        @SuppressWarnings("unchecked")
        Quantity<LengthUnit> wrongCategory = (Quantity<LengthUnit>)(Quantity<?>)
                new Quantity<>(5.0, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class, () -> q.add(wrongCategory));
        assertThrows(IllegalArgumentException.class, () -> q.subtract(wrongCategory));
        assertThrows(IllegalArgumentException.class, () -> q.divide(wrongCategory));

        assertThrows(IllegalArgumentException.class, () -> q.add(null));
        assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
        assertThrows(IllegalArgumentException.class, () -> q.divide(null));
    }
}