package quantityMeasurement;

public interface IMeasurable {

    SupportsArithmetic supportsArithmetic = () -> true;

    public String getUnitName();
    public double getConversionFactor();
    public double convertToBaseUnit(double value);
    public double convertFromBaseUnit(double baseValue);
    public String getMeasurementType();
    public IMeasurable getUnitInstance(String unitName);
    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
    }

    public static void main(String[] args) {
        System.out.println("IMeasurable Interface");
    }
}
