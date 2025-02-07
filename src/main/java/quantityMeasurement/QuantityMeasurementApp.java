package quantityMeasurement;

import java.util.*;

import length.LengthUnit;
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

    public static void main(String[] args) {

        Quantity<LengthUnit> l1 =
                new Quantity<>(2.0, LengthUnit.FEET);

        Quantity<LengthUnit> l2 =
                new Quantity<>(24.0, LengthUnit.INCHES);

        System.out.println("App Length Equality: " +
                demonstrateEquality(l1, l2));

        System.out.println("App Length Addition: " +
                demonstrateAddition(l1, l2));



        Quantity<WeightUnit> w1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> w2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println("App Weight Equality: " +
                demonstrateEquality(w1, w2));

        System.out.println("App Weight Addition: " +
                demonstrateAddition(w1, w2));
    }
}