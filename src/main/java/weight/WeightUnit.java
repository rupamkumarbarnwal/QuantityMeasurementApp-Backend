package weight;

public enum WeightUnit {
	MILLIGRAM(0.001),
	GRAM(1.0),
	KILOGRAM(1000.0),
	POUND(453.592),
	TONNE(1_000_000.0);
	private final double conversionFactor; //base =  kg
	private WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}
	public double getConversionFactor() {
		return conversionFactor;
	}
	public double convertToBaseUnit(double value) {
		return value * conversionFactor;
	}
	public double convertFromBaseUnit(double value) {
		return value/conversionFactor;
	}
	public static void main(String[] args) {
		double kiloGrams  = 10.0;
		double grams = WeightUnit.KILOGRAM.convertToBaseUnit(kiloGrams);
		System.out.println(kiloGrams +" Kilograms is "+grams+" grams");
		double miliGrams = WeightUnit.MILLIGRAM.convertToBaseUnit(grams);
		System.out.println(miliGrams+" Miligram is "+grams+ " grams");
	}
}

