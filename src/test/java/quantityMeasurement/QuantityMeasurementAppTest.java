package quantityMeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import length.LengthUnit;
import weight.WeightUnit;
import volume.VolumeUnit;
import temperature.TemperatureUnit;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Celsius32Fahrenheit() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0,  TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_100Celsius212Fahrenheit() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_Negative40Equal() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(-40.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0,  TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(t1.equals(t2));
        assertTrue(t2.equals(t1));
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertTrue(t1.equals(t1));
    }

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit_VariousValues() {
        assertEquals(122.0, new Quantity<>(50.0,  TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPSILON);
        assertEquals(-4.0,  new Quantity<>(-20.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPSILON);
        assertEquals(32.0,  new Quantity<>(0.0,   TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPSILON);
        assertEquals(212.0, new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius_VariousValues() {
        assertEquals(50.0,  new Quantity<>(122.0, TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS).getValue(), EPSILON);
        assertEquals(-20.0, new Quantity<>(-4.0,  TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS).getValue(), EPSILON);
        assertEquals(0.0,   new Quantity<>(32.0,  TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS).getValue(), EPSILON);
        assertEquals(100.0, new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS).getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_RoundTrip_PreservesValue() {
        double original = 75.0;
        Quantity<TemperatureUnit> celsius    = new Quantity<>(original, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = celsius.convertTo(TemperatureUnit.FAHRENHEIT);
        Quantity<TemperatureUnit> backToCelsius = fahrenheit.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(original, backToCelsius.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> celsius = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> result  = celsius.convertTo(TemperatureUnit.CELSIUS);
        assertEquals(100.0, result.getValue(), EPSILON);
        assertEquals(TemperatureUnit.CELSIUS, result.getUnit());
    }

    @Test
    void testTemperatureConversion_ZeroValue() {
        Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(32.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_NegativeValues() {
        assertEquals(-40.0, new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPSILON);
        assertEquals(-40.0, new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT)
                .convertTo(TemperatureUnit.CELSIUS).getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_LargeValues() {
        double expected = 1000.0 * 9.0 / 5.0 + 32.0;
        assertEquals(expected, new Quantity<>(1000.0, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT).getValue(), EPSILON);
    }

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.subtract(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.divide(t2));
    }

    @Test
    void testTemperatureUnsupportedOperation_ErrorMessage() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class,
                () -> t1.add(t2));
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains("does not support"));
    }

    @Test
    void testTemperatureVsLengthIncompatibility() {
        Quantity<TemperatureUnit> temp   = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<LengthUnit>      length = new Quantity<>(100.0, LengthUnit.FEET);
        assertFalse(temp.equals(length));
    }

    @Test
    void testTemperatureVsWeightIncompatibility() {
        Quantity<TemperatureUnit> temp   = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        Quantity<WeightUnit>      weight = new Quantity<>(50.0, WeightUnit.KILOGRAM);
        assertFalse(temp.equals(weight));
    }

    @Test
    void testTemperatureVsVolumeIncompatibility() {
        Quantity<TemperatureUnit> temp   = new Quantity<>(25.0, TemperatureUnit.CELSIUS);
        Quantity<VolumeUnit>      volume = new Quantity<>(25.0, VolumeUnit.LITRE);
        assertFalse(temp.equals(volume));
    }

    @Test
    void testOperationSupportMethods_TemperatureUnitAddition() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_TemperatureUnitDivision() {
        assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthUnitAddition() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_WeightUnitDivision() {
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
    }

    @Test
    void testIMeasurableInterface_Evolution_BackwardCompatible() {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0,  LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertEquals(2.0, l1.add(l2).getValue(), EPSILON);

        Quantity<WeightUnit> w1 = new Quantity<>(1.0,    WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(2.0, w1.add(w2).getValue(), EPSILON);

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0,    VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(2.0, v1.add(v2).getValue(), EPSILON);
    }

    @Test
    void testTemperatureUnit_NonLinearConversion() {
        double celsius    = 100.0;
        double fahrenheit = new Quantity<>(celsius, TemperatureUnit.CELSIUS)
                .convertTo(TemperatureUnit.FAHRENHEIT).getValue();
        assertNotEquals(celsius * TemperatureUnit.CELSIUS.getConversionFactor(), fahrenheit, EPSILON);
        assertEquals(212.0, fahrenheit, EPSILON);
    }

    @Test
    void testTemperatureUnit_AllConstants() {
        TemperatureUnit[] units = TemperatureUnit.values();
        assertEquals(2, units.length);
        assertEquals(TemperatureUnit.CELSIUS,    units[0]);
        assertEquals(TemperatureUnit.FAHRENHEIT, units[1]);
    }

    @Test
    void testTemperatureUnit_NameMethod() {
        assertEquals("CELSIUS",    TemperatureUnit.CELSIUS.getUnitName());
        assertEquals("FAHRENHEIT", TemperatureUnit.FAHRENHEIT.getUnitName());
    }

    @Test
    void testTemperatureUnit_ConversionFactor() {
        assertEquals(1.0, TemperatureUnit.CELSIUS.getConversionFactor(),    EPSILON);
        assertEquals(1.0, TemperatureUnit.FAHRENHEIT.getConversionFactor(), EPSILON);
    }

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(100.0, null));
    }

    @Test
    void testTemperatureNullOperandValidation_InComparison() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertFalse(t1.equals(null));
    }

    @Test
    void testTemperatureDifferentValuesInequality() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(50.0,  TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        assertFalse(t1.equals(t2));
    }

    @Test
    void testTemperatureBackwardCompatibility_UC1_Through_UC13() {
        assertEquals(2.0, new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCHES)).getValue(), EPSILON);
        assertEquals(9.5, new Quantity<>(10.0, LengthUnit.FEET)
                .subtract(new Quantity<>(6.0, LengthUnit.INCHES)).getValue(), EPSILON);
        assertEquals(5.0, new Quantity<>(10.0, LengthUnit.FEET)
                .divide(new Quantity<>(2.0, LengthUnit.FEET)), EPSILON);

        assertEquals(2.0, new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .add(new Quantity<>(1000.0, WeightUnit.GRAM)).getValue(), EPSILON);
        assertEquals(5.0, new Quantity<>(10.0, WeightUnit.KILOGRAM)
                .divide(new Quantity<>(2.0, WeightUnit.KILOGRAM)), EPSILON);

        assertEquals(2.0, new Quantity<>(1.0, VolumeUnit.LITRE)
                .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)).getValue(), EPSILON);
        assertEquals(5.0, new Quantity<>(10.0, VolumeUnit.LITRE)
                .divide(new Quantity<>(2.0, VolumeUnit.LITRE)), EPSILON);
    }

    @Test
    void testTemperatureConversionPrecision_Epsilon() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(0.0,  TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        double base1 = TemperatureUnit.CELSIUS.convertToBaseUnit(t1.getValue());
        double base2 = TemperatureUnit.FAHRENHEIT.convertToBaseUnit(t2.getValue());
        assertEquals(base1, base2, EPSILON);
    }

    @Test
    void testTemperatureConversionEdgeCase_VerySmallDifference() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0000001, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(100.0000002, TemperatureUnit.CELSIUS);
        double base1 = TemperatureUnit.CELSIUS.convertToBaseUnit(t1.getValue());
        double base2 = TemperatureUnit.CELSIUS.convertToBaseUnit(t2.getValue());
        assertEquals(base1, base2, 1e-6);
    }

    @Test
    void testTemperatureEnumImplementsIMeasurable() {
        assertTrue(TemperatureUnit.CELSIUS    instanceof IMeasurable);
        assertTrue(TemperatureUnit.FAHRENHEIT instanceof IMeasurable);
    }

    @Test
    void testTemperatureDefaultMethodInheritance() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
        assertTrue(LengthUnit.INCHES.supportsArithmetic());
        assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
        assertTrue(WeightUnit.GRAM.supportsArithmetic());
        assertTrue(VolumeUnit.LITRE.supportsArithmetic());
        assertTrue(VolumeUnit.MILLILITRE.supportsArithmetic());
    }

    @Test
    void testTemperatureCrossUnitAdditionAttempt() {
        Quantity<TemperatureUnit> celsius    = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> fahrenheit = new Quantity<>(50.0,  TemperatureUnit.FAHRENHEIT);
        assertThrows(UnsupportedOperationException.class, () -> celsius.add(fahrenheit));
    }

    @Test
    void testTemperatureValidateOperationSupport_MethodBehavior() {
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("addition"));
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.FAHRENHEIT.validateOperationSupport("subtraction"));
        assertThrows(UnsupportedOperationException.class,
                () -> TemperatureUnit.CELSIUS.validateOperationSupport("divide"));
    }

    @Test
    void testTemperatureIntegrationWithGenericQuantity() {
        Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        assertNotNull(temp);
        assertEquals(100.0,              temp.getValue(), EPSILON);
        assertEquals(TemperatureUnit.CELSIUS, temp.getUnit());

        Quantity<TemperatureUnit> converted = temp.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0,                   converted.getValue(), EPSILON);
        assertEquals(TemperatureUnit.FAHRENHEIT, converted.getUnit());

        assertTrue(temp.equals(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT)));
        assertFalse(temp.equals(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));

        assertThrows(UnsupportedOperationException.class,
                () -> temp.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }
}