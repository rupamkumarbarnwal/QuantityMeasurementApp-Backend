package quantityMeasurement;
import java.util.Objects;

import length.LengthUnit;
import weight.WeightUnit;

public class Quantity<U extends IMeasurable> {
	private  double value;
	private  U unit;
	public Quantity(double value, U unit) {
		if(unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
			
		}
		if(!Double.isFinite(value)) {
			throw new IllegalArgumentException("Invalid numberic value");
		}
		this.value = value;
		this.unit = unit;
		
	}
	public double getValue() {
		return value;
	}
	public U getUnit() {
		return unit;
	}
	public Quantity<U>  convertTo(U targetUnit) {
		if(targetUnit==null) {
			throw new IllegalArgumentException("Target unit cannot be null");
			
		}
		if (!unit.getClass().equals(targetUnit.getClass()))
		    throw new IllegalArgumentException("Incompatible units");
		double baseValue = unit.convertToBaseUnit(value);
		double convertedValue = targetUnit.convertFromBaseUnit(baseValue);
		return new Quantity<>(convertedValue, targetUnit);
		
	}
	public Quantity<U> add(Quantity<U> other){
		return add(other, this.unit);
	}
	 public Quantity<U> add(Quantity<U> other, U targetUnit) {
	        if (other == null)
	            throw new IllegalArgumentException("Other quantity cannot be null");

	        if (this.unit.getClass() != other.unit.getClass())
	            throw new IllegalArgumentException("Incompatible unit categories");

	        double baseValue1 = this.unit.convertToBaseUnit(this.value);
	        double baseValue2 = other.unit.convertToBaseUnit(other.value);

	        double sumBase = baseValue1 + baseValue2;

	        double finalValue = targetUnit.convertFromBaseUnit(sumBase);

	        return new Quantity<>(finalValue, targetUnit);
	    }
	 @Override
	 public boolean equals(Object obj) {

	     if (this == obj) return true;

	     if (obj == null) return false;

	     if (!(obj instanceof Quantity))
	         return false;

	     Quantity<?> other = (Quantity<?>) obj;

	     if (this.unit.getClass() != other.unit.getClass())
	         return false;

	     double base1 = this.unit.convertToBaseUnit(this.value);
	     double base2 = other.unit.convertToBaseUnit(other.value);

	     return Double.compare(base1, base2) == 0;
	 }
	 @Override
	    public int hashCode() {
	        double baseValue = unit.convertToBaseUnit(value);
	        return Objects.hash(baseValue, unit.getClass());
	    }

	    @Override
	    public String toString() {
	        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	    }
	    public static void main(String[] args) {

	        Quantity<LengthUnit> length1 =
	                new Quantity<>(1.0, LengthUnit.FEET);

	        Quantity<LengthUnit> length2 =
	                new Quantity<>(12.0, LengthUnit.INCHES);

	        System.out.println("Length Equality: " +
	                length1.equals(length2));

	        System.out.println("Length Addition: " +
	                length1.add(length2));

	        System.out.println("Convert Feet to Yards: " +
	                length1.convertTo(LengthUnit.YARDS));

	        Quantity<WeightUnit> weight1 =
	                new Quantity<>(1.0, WeightUnit.KILOGRAM);

	        Quantity<WeightUnit> weight2 =
	                new Quantity<>(1000.0, WeightUnit.GRAM);

	        System.out.println("Weight Equality: " +
	                weight1.equals(weight2));

	        System.out.println("Weight Addition: " +
	                weight1.add(weight2));

	        System.out.println("Convert KG to Pound: " +
	                weight1.convertTo(WeightUnit.POUND));
	    }
}
