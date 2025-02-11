package length;

import quantityMeasurement.IMeasurable;

public enum LengthUnit implements IMeasurable {

	FEET(12.0),
	INCHES(1.0), 
	YARDS(36.0),
	CENTIMETERS(0.393701);

	private final double conversionFactor;

	LengthUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value * conversionFactor;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue / conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}
	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}
	
	@Override 
	public IMeasurable getUnitInstance(String unitName) {
		for(LengthUnit unit : LengthUnit.values()) {
			if(unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid length unit: "+ unitName);
	}

	public static void main(String[] args) {

		double feet = 2.0;

		double inches = LengthUnit.FEET.convertToBaseUnit(feet);
		System.out.println(feet + " FEET in base (INCHES) = " + inches);

		double backToFeet = LengthUnit.FEET.convertFromBaseUnit(inches);
		System.out.println(inches + " INCHES in FEET = " + backToFeet);

	}
}
