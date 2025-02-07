package quantityMeasurement;

import java.util.*;

import length.Length;
import length.LengthUnit;
import weight.Weight;
import weight.WeightUnit;

public class QuantityMeasurementApp {

	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	public static boolean demonstrateLengthComparison(double v1, LengthUnit u1, double v2, LengthUnit u2) {

		Length l1 = new Length(v1, u1);
		Length l2 = new Length(v2, u2);

		return l1.equals(l2);
	}

	public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {

		Length length = new Length(value, fromUnit);
		return length.convertTo(toUnit);
	}

	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {

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
	 public static boolean demonstrateWeightEquality(Weight w1, Weight w2) {
	        return w1.equals(w2);
	    }

	    public static boolean demonstrateWeightComparison(double v1, WeightUnit u1,
	                                                      double v2, WeightUnit u2) {
	        return new Weight(v1, u1)
	                .equals(new Weight(v2, u2));
	    }

	    public static Weight demonstrateWeightConversion(double value,
	                                                             WeightUnit from,
	                                                             WeightUnit to) {
	        return new Weight(value, from).convertTo(to);
	    }

	    public static Weight demonstrateWeightConversion(Weight weight,
	                                                             WeightUnit to) {
	        return weight.convertTo(to);
	    }

	    public static Weight demonstrateWeightAddition(Weight w1,
	                                                           Weight w2) {
	        return w1.add(w2);
	    }

	    public static Weight demonstrateWeightAddition(Weight w1,
	                                                           Weight w2,
	                                                           WeightUnit targetUnit) {
	        return w1.add(w2, targetUnit);
	    }

	public static void main(String[] args) {
		  Weight kg = new Weight(1.0, WeightUnit.KILOGRAM);
	        Weight gram = new Weight(1000.0, WeightUnit.GRAM);
	        Weight pound = new Weight(2.20462, WeightUnit.POUND);

	       
	        System.out.println(demonstrateWeightEquality(kg, gram));   // true
	        System.out.println(demonstrateWeightEquality(kg, pound));  // true

	       
	        System.out.println(demonstrateWeightConversion(kg, WeightUnit.GRAM));
	        System.out.println(demonstrateWeightConversion(pound, WeightUnit.KILOGRAM));

	      
	        System.out.println(demonstrateWeightAddition(kg, gram));
	        System.out.println(demonstrateWeightAddition(kg, gram, WeightUnit.GRAM));
	}
}