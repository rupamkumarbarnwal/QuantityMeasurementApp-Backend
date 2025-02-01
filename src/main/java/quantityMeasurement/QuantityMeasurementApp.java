package quantityMeasurement;

import java.util.*;

import length.Length;
public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static boolean demonstrateLengthComparison(
            double v1, Length.LengthUnit u1,
            double v2, Length.LengthUnit u2) {

        Length l1 = new Length(v1, u1);
        Length l2 = new Length(v2, u2);

        return l1.equals(l2);
    }

 

    public static double demonstrateLengthConversion(
            double value,
            Length.LengthUnit from,
            Length.LengthUnit to) {

        return Length.convert(value, from, to);
    }

    public static Length demonstrateLengthConversion(
            Length length,
            Length.LengthUnit toUnit) {

        return length.convertTo(toUnit);
    }

    

    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        return l1.add(l2);
    }

    public static Length demonstrateLengthAddition(
            double v1, Length.LengthUnit u1,
            double v2, Length.LengthUnit u2) {

        Length l1 = new Length(v1, u1);
        Length l2 = new Length(v2, u2);

        return l1.add(l2);
    }

    public static void main(String[] args) {

        System.out.println("1 ft to inches: " +
                demonstrateLengthConversion(1.0,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES));

        Length result = demonstrateLengthAddition(
                1.0, Length.LengthUnit.FEET,
                12.0, Length.LengthUnit.INCHES);

        System.out.println("1 ft + 12 in = " + result);
    }
}	