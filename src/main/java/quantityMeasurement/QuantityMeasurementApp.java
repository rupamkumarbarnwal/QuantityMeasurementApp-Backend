package quantityMeasurement;

import java.util.*;

import length.LengthUnit;
import volume.VolumeUnit;
import weight.WeightUnit;

public class QuantityMeasurementApp {

	public static <U extends IMeasurable>
    boolean demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
        return q1.equals(q2);
    }

    public static <U extends IMeasurable>
    Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {
        return quantity.convertTo(targetUnit);
    }

    public static <U extends IMeasurable>
    Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2) {
        return q1.add(q2);
    }

    public static <U extends IMeasurable>
    Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        return q1.add(q2, targetUnit);
    }
	public static <U extends IMeasurable>
	Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {
		return q1.subtract(q2);
	}
	public static <U extends IMeasurable>
	Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
		return q1.subtract(q2, targetUnit);
    }
	public static <U extends IMeasurable>
	double demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
		return q1.divide(q2);
	}
	
    public static void main(String[] args) {

        Quantity<LengthUnit> l1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> l2 =
                new Quantity<>(6.0, LengthUnit.INCHES);


		System.out.println("Subtract Length: " +
				demonstrateSubtraction(l1, l2));
		System.out.println("Divide Length: " +
				demonstrateDivision(l1, l2));
		System.out.println("=====================");

        Quantity<WeightUnit> w1 =
                new Quantity<>(10.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> w2 =
                new Quantity<>(5000.0, WeightUnit.GRAM);

		System.out.println("Subtract Weight: " +
				demonstrateSubtraction(w1, w2));
		System.out.println("Divide Weight: " +
				demonstrateDivision(w1, w2));
        System.out.println("=====================");
        Quantity<VolumeUnit> v1 =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> v2 =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        
		System.out.println("Subtract Volume: " +
				demonstrateSubtraction(v1, v2));
		System.out.println("Divide Volume: " +
				demonstrateDivision(v1, v2));
    }
}