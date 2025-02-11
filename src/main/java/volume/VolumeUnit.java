package volume;
import length.LengthUnit;
import quantityMeasurement.IMeasurable;

public enum VolumeUnit implements IMeasurable {
		LITRE(1.0),
		MILLILITRE(0.001),
		GALLON(3.78541);

	private final double conversionFactor;

	VolumeUnit(double conversionFactor) {
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
		for(VolumeUnit unit : VolumeUnit.values()) {
			if(unit.getUnitName().equalsIgnoreCase(unitName)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("Invalid length unit: "+ unitName);
	}
	public static void main(String[] args) {

		double millilitre = 2000.0;

		double litre = VolumeUnit.MILLILITRE.convertToBaseUnit(millilitre);
		System.out.println(millilitre+ " millilitre in base (litre) = " + litre);
		
		double backToMillilitre =VolumeUnit.MILLILITRE.convertFromBaseUnit(litre);
		System.out.println(litre + " litre in millilitre = " + backToMillilitre);

	}

}
