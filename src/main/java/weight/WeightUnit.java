package weight;

import quantityMeasurement.IMeasurable;

public enum WeightUnit implements IMeasurable {
	MILLIGRAM(0.001),
	GRAM(1.0), 
	KILOGRAM(1000.0),
	POUND(453.592),
	TONNE(1000000.0);

	private final double conversionFactor;

	private WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value * conversionFactor;
	}

	@Override
	public double convertFromBaseUnit(double basevalue) {
		return basevalue / conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	public static void main(String[] args) {

		double kg = 2.0;

		double grams = WeightUnit.KILOGRAM.convertToBaseUnit(kg);
		System.out.println(kg + " KILOGRAM in base (GRAMS) = " + grams);

		double backToKg = WeightUnit.KILOGRAM.convertFromBaseUnit(grams);
		System.out.println(grams + " GRAMS in KILOGRAM = " + backToKg);

	}
}
