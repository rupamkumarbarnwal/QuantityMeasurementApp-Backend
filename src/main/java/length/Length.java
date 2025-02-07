package length;

import java.util.Objects;

public final class Length {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final LengthUnit unit;

	
	public Length(double value, LengthUnit unit) {
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public LengthUnit getUnit() {
		return unit;
	}

	private double toBaseUnit() {
		return unit.toInches(value);
	}

	public static double convert(double value, LengthUnit source, LengthUnit target) {

		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}
		if (source == null || target == null) {
			throw new IllegalArgumentException("Units cannot be null");
		}

		double inInches = source.toInches(value);
		return inInches / target.getConversionFactor();
	}

	
	  public Length convertTo(LengthUnit targetUnit) {

	        double baseValue = unit.convertToBaseUnit(value);
	        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

	        return new Length(convertedValue, targetUnit);
	    }
	   public Length add(Length other) {
	        return add(other, this.unit);
	    }

	public static Length add(Length l1, Length l2, LengthUnit targetUnit) {

		if (l1 == null || l2 == null) {
			throw new IllegalArgumentException("Operands cannot be null");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}

		double sumInInches = l1.toBaseUnit() + l2.toBaseUnit();
		double resultValue = sumInInches / targetUnit.getConversionFactor();

		return new Length(resultValue, targetUnit);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Length other = (Length) obj;

		return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Math.round(toBaseUnit() / EPSILON));
	}

	@Override
	public String toString() {
		return String.format("%.6f %s", value, unit);
	}

	private Length addAndConvert(Length other, LengthUnit targetUnit) {

		double sumInInches = this.toBaseUnit() + other.toBaseUnit();

		double resultValue = sumInInches / targetUnit.getConversionFactor();

		return new Length(resultValue, targetUnit);
	}
	public Length add(Length other, LengthUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Length to add cannot be null");
        }

        double baseSum =
                this.unit.convertToBaseUnit(this.value) +
                other.unit.convertToBaseUnit(other.value);

        double resultValue = targetUnit.convertFromBaseUnit(baseSum);

        return new Length(resultValue, targetUnit);
    }

	public static void main(String[] args) {

		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);

		Length result = l1.add(l2);

		System.out.println("Addition Result: " + result);
	}
}