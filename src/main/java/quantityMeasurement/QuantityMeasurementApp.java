package quantityMeasurement;

import length.Length;


public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static boolean demonstrateLengthComparison(
            double value1, Length.LengthUnit unit1,
            double value2, Length.LengthUnit unit2) {

        Length l1 = new Length(value1, unit1);
        Length l2 = new Length(value2, unit2);

        return demonstrateLengthEquality(l1, l2);
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

    public static void main(String[] args) {

        System.out.println("1 foot to inches: " +
                demonstrateLengthConversion(1.0,
                        Length.LengthUnit.FEET,
                        Length.LengthUnit.INCHES));

        System.out.println("3 yards to feet: " +
                demonstrateLengthConversion(3.0,
                        Length.LengthUnit.YARDS,
                        Length.LengthUnit.FEET));

        System.out.println("Equality check (1 yard == 3 feet): " +
                demonstrateLengthComparison(1.0,
                        Length.LengthUnit.YARDS,
                        3.0,
                        Length.LengthUnit.FEET));
    }
}