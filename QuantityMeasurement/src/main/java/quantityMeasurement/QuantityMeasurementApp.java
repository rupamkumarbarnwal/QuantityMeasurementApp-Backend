package quantityMeasurement;

import length.Length;

public class QuantityMeasurementApp {

	public static void demonstrateFeeInchesComparison() {
		Length length1 = new Length(1.0, Length.LengthUnit.FEET);
		Length length2 = new Length(12.0, Length.LengthUnit.INCHES);

		boolean result = length1.equals(length2);

		System.out.println("Input: 1.0 ft and 12.0 Inches");
		System.out.println("Output: Equal (" + result + ")");
	}

	public static void demonstrateFeetEquality() {
		Length feet1 = new Length(1.0, Length.LengthUnit.FEET);
		Length feet2 = new Length(1.0, Length.LengthUnit.FEET);

		boolean result = feet1.equals(feet2);

		System.out.println("Input: 1.0 ft and 1.0 ft");
		System.out.println("Output: Equal (" + result + ")");
	}

	public static void demonstrateInchesEquality() {
		Length inch1 = new Length(1.0, Length.LengthUnit.INCHES);
		Length inch2 = new Length(1.0, Length.LengthUnit.INCHES);
		boolean result = inch1.equals(inch2);

		System.out.println("Input: 1.0 Inches and 1.0 Inches");
		System.out.println("Output: Equal (" + result + ")");
	}

	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}

	public static void main(String[] args) {

		demonstrateFeetEquality();
		demonstrateInchesEquality();
		demonstrateFeeInchesComparison();
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
