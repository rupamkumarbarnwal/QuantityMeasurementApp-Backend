package quantityMeasurement;

import java.util.*;

import length.Length;

public class QuantityMeasurementApp {

	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	public static boolean demonstrateLengthComparison(double v1, Length.LengthUnit u1, double v2,
			Length.LengthUnit u2) {

		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);

		return l1.equals(l2);
	}

	public static double demonstrateLengthConversion(double value, Length.LengthUnit from, Length.LengthUnit to) {

		return Length.convert(value, from, to);
	}

	public static Length demonstrateLengthConversion(Length length, Length.LengthUnit toUnit) {

		return length.convertTo(toUnit);
	}

	public static Length demonstrateLengthAddition(Length l1, Length l2) {
		return l1.add(l2);
	}

	public static Length demonstrateLengthAddition(double v1, Length.LengthUnit u1, double v2, Length.LengthUnit u2) {

		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);

		return l1.add(l2);
	}

	public static Length demonstrateLengthAddition(Length l1, Length l2, Length.LengthUnit targetUnit) {

		return l1.add(l2, targetUnit);
	}

	public static void main(String[] args) {

		System.out.println("1 ft to inches: "
				+ demonstrateLengthConversion(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));

		Length result = demonstrateLengthAddition(1.0, Length.LengthUnit.FEET, 12.0, Length.LengthUnit.INCHES);

		System.out.println("1 ft + 12 in = " + result);
		Length l1 = new Length(1.0, Length.LengthUnit.FEET);
		Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

		Length resultFeet = l1.add(l2, Length.LengthUnit.FEET);
		Length resultInches = l1.add(l2, Length.LengthUnit.INCHES);
		Length resultYards = l1.add(l2, Length.LengthUnit.YARDS);

		System.out.println("Result in FEET: " + resultFeet);
		System.out.println("Result in INCHES: " + resultInches);
		System.out.println("Result in YARDS: " + resultYards);
	}
}