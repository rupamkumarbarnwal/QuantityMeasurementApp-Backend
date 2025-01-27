package quantityMeasurement;

import length.Length;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static void main(String[] args) {

        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);

        System.out.println("Input: " + length1 + " and " + length2);
        System.out.println("Output: Equal (" + demonstrateLengthEquality(length1, length2) + ")");

        Length length3 = new Length(1.0, Length.LengthUnit.INCHES);
        Length length4 = new Length(1.0, Length.LengthUnit.INCHES);

        System.out.println("Input: " + length3 + " and " + length4);
        System.out.println("Output: Equal (" + demonstrateLengthEquality(length3, length4) + ")");
    }
}
