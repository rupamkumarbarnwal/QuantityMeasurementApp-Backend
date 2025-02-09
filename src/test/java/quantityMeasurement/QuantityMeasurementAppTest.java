package quantityMeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import length.LengthUnit;
import weight.WeightUnit;
import volume.VolumeUnit;


public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testEquality_LitreToLitre_SameValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_LitreToLitre_DifferentValue() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_LitreToMillilitre_EquivalentValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testEquality_MillilitreToLitre_EquivalentValue() {
        assertTrue(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_LitreToGallon_EquivalentValue() {
        Quantity<VolumeUnit> l = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> g = new Quantity<>(0.264172, VolumeUnit.GALLON);

        assertEquals(g.getValue(),
                l.convertTo(VolumeUnit.GALLON).getValue(),
                EPSILON);
    }

    @Test
    void testEquality_GallonToLitre_EquivalentValue() {
        Quantity<VolumeUnit> g = new Quantity<>(1.0, VolumeUnit.GALLON);

        assertEquals(3.78541,
                g.convertTo(VolumeUnit.LITRE).getValue(),
                EPSILON);
    }

    @Test
    void testEquality_VolumeVsLength_Incompatible() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> l = new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(v.equals(l));
    }

    @Test
    void testEquality_VolumeVsWeight_Incompatible() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> w = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(v.equals(w));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertTrue(v.equals(v));
    }

    @Test
    void testEquality_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testEquality_TransitiveProperty() {

        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> c = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(a.equals(b) && b.equals(c) && a.equals(c));
    }

    @Test
    void testEquality_ZeroValue() {
        assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testEquality_NegativeVolume() {
        assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testEquality_LargeVolumeValue() {
        assertTrue(new Quantity<>(1000000.0, VolumeUnit.MILLILITRE)
                .equals(new Quantity<>(1000.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_SmallVolumeValue() {
        assertTrue(new Quantity<>(0.001, VolumeUnit.LITRE)
                .equals(new Quantity<>(1.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testConversion_LitreToMillilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue());
    }

    @Test
    void testConversion_MillilitreToLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(1.0, result.getValue());
    }

    @Test
    void testConversion_GallonToLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(3.78541, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_LitreToGallon() {

        Quantity<VolumeUnit> result =
                new Quantity<>(3.78541, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.GALLON);

        assertEquals(1.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_MillilitreToGallon() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.GALLON);

        assertEquals(0.264172, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_SameUnit() {

        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(5.0, result.getValue());
    }

    @Test
    void testConversion_ZeroValue() {

        Quantity<VolumeUnit> result =
                new Quantity<>(0.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(0.0, result.getValue());
    }

    @Test
    void testConversion_NegativeValue() {

        Quantity<VolumeUnit> result =
                new Quantity<>(-1.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(-1000.0, result.getValue());
    }

    @Test
    void testConversion_RoundTrip() {

        Quantity<VolumeUnit> q =
                new Quantity<>(1.5, VolumeUnit.LITRE);

        assertEquals(q.getValue(),
                q.convertTo(VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.LITRE)
                        .getValue(),
                EPSILON);
    }


    @Test
    void testAddition_SameUnit_LitrePlusLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(2.0, VolumeUnit.LITRE));

        assertEquals(3.0, result.getValue());
    }

    @Test
    void testAddition_SameUnit_MillilitrePlusMillilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE)
                        .add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));

        assertEquals(1000.0, result.getValue());
    }

    @Test
    void testAddition_CrossUnit_LitrePlusMillilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testAddition_CrossUnit_MillilitrePlusLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .add(new Quantity<>(1.0, VolumeUnit.LITRE));

        assertEquals(2000.0, result.getValue());
    }

    @Test
    void testAddition_CrossUnit_GallonPlusLitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .add(new Quantity<>(3.78541, VolumeUnit.LITRE));

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Litre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                                VolumeUnit.LITRE);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Millilitre() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                                VolumeUnit.MILLILITRE);

        assertEquals(2000.0, result.getValue());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Gallon() {

        Quantity<VolumeUnit> result =
                new Quantity<>(3.78541, VolumeUnit.LITRE)
                        .add(new Quantity<>(3.78541, VolumeUnit.LITRE),
                                VolumeUnit.GALLON);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_Commutativity() {

        Quantity<VolumeUnit> a =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        Quantity<VolumeUnit> b =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                        .add(new Quantity<>(1.0, VolumeUnit.LITRE))
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(a.getValue(), b.getValue());
    }

    @Test
    void testAddition_WithZero() {

        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));

        assertEquals(5.0, result.getValue());
    }

    @Test
    void testAddition_NegativeValues() {

        Quantity<VolumeUnit> result =
                new Quantity<>(5.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));

        assertEquals(3.0, result.getValue());
    }

    @Test
    void testAddition_LargeValues() {

        Quantity<VolumeUnit> result =
                new Quantity<>(1e6, VolumeUnit.LITRE)
                        .add(new Quantity<>(1e6, VolumeUnit.LITRE));

        assertEquals(2e6, result.getValue());
    }

    @Test
    void testAddition_SmallValues() {

        Quantity<VolumeUnit> result =
                new Quantity<>(0.001, VolumeUnit.LITRE)
                        .add(new Quantity<>(0.002, VolumeUnit.LITRE));

        assertEquals(0.003, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor());
    }

    @Test
    void testVolumeUnitEnum_MillilitreConstant() {
        assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor());
    }

    @Test
    void testVolumeUnitEnum_GallonConstant() {
        assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor());
    }

    @Test
    void testConvertToBaseUnit_LitreToLitre() {
        assertEquals(5.0,
                VolumeUnit.LITRE.convertToBaseUnit(5.0));
    }

    @Test
    void testConvertToBaseUnit_MillilitreToLitre() {
        assertEquals(1.0,
                VolumeUnit.MILLILITRE.convertToBaseUnit(1000.0));
    }

    @Test
    void testConvertToBaseUnit_GallonToLitre() {
        assertEquals(3.78541,
                VolumeUnit.GALLON.convertToBaseUnit(1.0),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_LitreToLitre() {
        assertEquals(2.0,
                VolumeUnit.LITRE.convertFromBaseUnit(2.0));
    }

    @Test
    void testConvertFromBaseUnit_LitreToMillilitre() {
        assertEquals(1000.0,
                VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0));
    }

    @Test
    void testConvertFromBaseUnit_LitreToGallon() {
        assertEquals(1.0,
                VolumeUnit.GALLON.convertFromBaseUnit(3.78541),
                EPSILON);
    }


    @Test
    void testBackwardCompatibility_AllUC1Through10Tests() {
        assertTrue(new Quantity<>(1.0, LengthUnit.FEET)
                .equals(new Quantity<>(12.0, LengthUnit.INCHES)));
    }

    @Test
    void testGenericQuantity_VolumeOperations_Consistency() {
        Quantity<VolumeUnit> v =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertEquals(1000.0,
                v.convertTo(VolumeUnit.MILLILITRE).getValue());
    }

    @Test
    void testScalability_VolumeIntegration() {
        Quantity<VolumeUnit> v =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertNotNull(v);
    }
}