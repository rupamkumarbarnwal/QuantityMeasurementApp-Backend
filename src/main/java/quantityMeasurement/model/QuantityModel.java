package quantityMeasurement.model;

import quantityMeasurement.IMeasurable;

public class QuantityModel<U extends IMeasurable> {
	public double value;
	public U unit;
	 public QuantityModel(double value, U unit) {
	        this.value = value;
	        this.unit = unit;
	    }
	 public double getValue() {
	        return value;
	    }
	  public U getUnit() {
	        return unit;
	    }
	   public void setValue(double value) {
	        this.value = value;
	    }
	   public void setUnit(U unit) {
	        this.unit = unit;
	    }
	   @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (!(obj instanceof QuantityModel)) return false;
	        QuantityModel<?> other = (QuantityModel<?>) obj;
	        return Double.compare(this.value, other.value) == 0 &&
	               this.unit.equals(other.unit);
	    }
	   @Override
	    public int hashCode() {
	        return java.util.Objects.hash(value, unit);
	    }
	   @Override
	    public String toString() {
	        return value + " " + unit.getUnitName();
	    }
	   public static void main(String[] args) {
	        System.out.println("QuantityModel class");
	    }

}
