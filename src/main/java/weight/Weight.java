package weight;

import java.util.Objects;
public  class Weight {
	private static final double EPSILON = 1e-2;
	private double value;
	private WeightUnit unit;
	public Weight(double value,WeightUnit unit) {
		if(unit ==null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		this.value = value;
		this.unit = unit;
		
	}
	public WeightUnit getUnit() {
		return unit;
	}
	public double getValue() {
		return value;
	}
	 public double convertToBaseUnit() {
	        return unit.convertToBaseUnit(value);
	    }
	 public double convertFromBaseToTarget(double baseValue, WeightUnit targetUnit) {
	        return targetUnit.convertFromBaseUnit(baseValue);
	    }
	 public boolean compare(double baseValue1, double baseValue2) {
	        return Math.abs(baseValue1 - baseValue2) < EPSILON;
	    }
	 public Weight addAndConvert(Weight other, WeightUnit targetUnit) {

	        double base1 = this.convertToBaseUnit();
	        double base2 = other.convertToBaseUnit();

	        double sumBase = base1 + base2;

	        double finalValue = convertFromBaseToTarget(sumBase, targetUnit);

	        return new Weight(finalValue, targetUnit);
	    }
	public Weight convertTo(WeightUnit target) {
		if(target == null) {
			throw new IllegalArgumentException("target cannot be null");
		}
		double base = unit.convertToBaseUnit(value);
		double convertedValue = target.convertFromBaseUnit(base);
		return new Weight(convertedValue, target);
	}
	public Weight add(Weight another) {
		return add(another,this.unit);
	}
	public Weight add(Weight other, WeightUnit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue1 = this.unit.convertToBaseUnit(this.value);
        double baseValue2 = other.unit.convertToBaseUnit(other.value);

        double sumBase = baseValue1 + baseValue2;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new Weight(result, targetUnit);
    }
	   @Override
	    public boolean equals(Object obj) {

	        if (this == obj) return true;

	        if (obj == null || getClass() != obj.getClass()) return false;

	        Weight other = (Weight) obj;

	        double thisBase = this.unit.convertToBaseUnit(this.value);
	        double otherBase = other.unit.convertToBaseUnit(other.value);

	        return Math.abs(thisBase - otherBase) < EPSILON;
	    }
	   @Override
	    public int hashCode() {
	        double baseValue = unit.convertToBaseUnit(value);
	        return Objects.hash(Math.round(baseValue / EPSILON));
	    }

	    @Override
	    public String toString() {
	        return String.format("Quantity(%.6f, %s)", value, unit);
	    }
	
}