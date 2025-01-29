package quantityMeasurement;

import length.Length;


public class QuantityMeasurementApp {

    public static boolean demonstrateLengthComparison(
            double value1, Length.LengthUnit unit1,
            double value2, Length.LengthUnit unit2) {

        Length l1 = new Length(value1, unit1);
        Length l2 = new Length(value2, unit2);

        return l1.equals(l2);
    }

    public static void main(String[] args) {

        System.out.println("Yard to Feet: " +
                demonstrateLengthComparison(1.0,
                        Length.LengthUnit.YARDS,
                        3.0,
                        Length.LengthUnit.FEET));

        System.out.println("Yard to Inches: " +
                demonstrateLengthComparison(1.0,
                        Length.LengthUnit.YARDS,
                        36.0,
                        Length.LengthUnit.INCHES));

        System.out.println("CM to Inches: " +
                demonstrateLengthComparison(1.0,
                        Length.LengthUnit.CENTIMETERS,
                        0.393701,
                        Length.LengthUnit.INCHES));
    }
}