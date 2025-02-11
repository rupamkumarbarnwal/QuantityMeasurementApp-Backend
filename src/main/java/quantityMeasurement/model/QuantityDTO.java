package quantityMeasurement.model;

public class QuantityDTO {
	private final double value;
	private final String unitName;
	private final String measurementType;
	
	public interface IMeasurableUnit{
		String getUnitName();
		String getMeasurementType();
	}
	 public enum LengthUnit implements IMeasurableUnit {
	        INCHES, FEET, YARD, CENTIMETER;

	        @Override
	        public String getUnitName() {
	            return this.name();
	        }

	        @Override
	        public String getMeasurementType() {
	            return "LengthUnit";
	        }
	    }
	 public enum WeightUnit implements IMeasurableUnit {
			MILLIGRAM,
			GRAM, 
			KILOGRAM,
			POUND,
			TONNE;

	        @Override
	        public String getUnitName() {
	            return this.name();
	        }

	        @Override
	        public String getMeasurementType() {
	            return "WeightUnit";
	        }
	    }

	    public enum VolumeUnit implements IMeasurableUnit {
	        MILLILITRE, LITRE, GALLON;
	        @Override
	        public String getUnitName() {
	            return this.name();
	        }

	        @Override
	        public String getMeasurementType() {
	            return "VolumeUnit";
	        }
	    }
	    public enum TemperatureUnit implements IMeasurableUnit {
	        CELSIUS, FAHRENHEIT;

	        @Override
	        public String getUnitName() {
	            return this.name();
	        }

	        @Override
	        public String getMeasurementType() {
	            return "TemperatureUnit";
	        }
	    }
	    public QuantityDTO(double value, IMeasurableUnit unit) {
	        this.value = value;
	        this.unitName = unit.getUnitName();
	        this.measurementType = unit.getMeasurementType();
	    }
	    public QuantityDTO(double value, String unitName, String measurementType) {
	        this.value = value;
	        this.unitName = unitName;
	        this.measurementType = measurementType;
	    }
	    public double getValue() {
	        return value;
	    }

	    public String getUnitName() {
	        return unitName;
	    }
	    public String getUnit() {
	        return unitName;
	    }

	    public String getMeasurementType() {
	        return measurementType;
	    }
	    @Override
	    public boolean equals(Object obj) {
	        if (!(obj instanceof QuantityDTO)) return false;
	        QuantityDTO other = (QuantityDTO) obj;
	        return this.value == other.value &&
	               this.unitName.equals(other.unitName) &&
	               this.measurementType.equals(other.measurementType);
	    }

	    @Override
	    public int hashCode() {
	        return java.util.Objects.hash(value, unitName, measurementType);
	    }
	    @Override
	    public String toString() {
	        return value + " " + unitName;
	    }
	    public static void main(String[] args) {
	        QuantityDTO q1 = new QuantityDTO(2.0, LengthUnit.FEET);
	        System.out.println("Value: "           + q1.getValue());
	        System.out.println("Unit: "            + q1.getUnitName());
	        System.out.println("MeasurementType: " + q1.getMeasurementType());
	        System.out.println("toString: "        + q1);

	        QuantityDTO q2 = new QuantityDTO(2.0, "FEET", "LengthUnit");
	        System.out.println("Equal: " + q1.equals(q2));
	    }
}
