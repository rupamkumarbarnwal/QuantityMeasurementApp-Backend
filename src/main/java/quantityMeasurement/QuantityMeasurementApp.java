package quantityMeasurement;

import java.util.*;

import length.Length;
import length.LengthUnit;

public class QuantityMeasurementApp {

	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	public static boolean demonstrateLengthComparison(double v1, LengthUnit u1, double v2,
			LengthUnit u2) {

		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);

		return l1.equals(l2);
	}

	  public static Length demonstrateLengthConversion(
	            double value,
	            LengthUnit fromUnit,
	            LengthUnit toUnit) {

	        Length length = new Length(value, fromUnit);
	        return length.convertTo(toUnit);
	    }

	  public static Length demonstrateLengthConversion(
	            Length length,
	            LengthUnit toUnit) {

	        return length.convertTo(toUnit);
	    }

	public static Length demonstrateLengthAddition(Length l1, Length l2) {
		return l1.add(l2);
	}

	public static Length demonstrateLengthAddition(double v1, LengthUnit u1, double v2, LengthUnit u2) {

		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);

		return l1.add(l2);
	}

	public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit targetUnit) {

		return l1.add(l2, targetUnit);
	}

	public static void main(String[] args) {

		System.out.println("1 ft to inches: "
				+ demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES));

		Length result = demonstrateLengthAddition(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);

		System.out.println("1 ft + 12 in = " + result);
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);

		Length resultFeet = l1.add(l2, LengthUnit.FEET);
		Length resultInches = l1.add(l2, LengthUnit.INCHES);
		Length resultYards = l1.add(l2, LengthUnit.YARDS);
	     System.out.println(l1.convertTo(LengthUnit.INCHES));
	        System.out.println(l1.add(l2, LengthUnit.FEET));
	        System.out.println(l1.equals(l2));
		System.out.println("Result in FEET: " + resultFeet);
		System.out.println("Result in INCHES: " + resultInches);
		System.out.println("Result in YARDS: " + resultYards);
	}
}