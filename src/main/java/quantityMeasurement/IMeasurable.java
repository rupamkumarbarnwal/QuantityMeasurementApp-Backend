package quantityMeasurement;

public interface IMeasurable {

    SupportsArithmetic supportsArithmetic = () -> true;

    public String getUnitName();
    public double getConversionFactor();
    public double convertToBaseUnit(double value);
    public double convertFromBaseUnit(double baseValue);

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // Subclasses can override to validate specific operations
    }

    public static void main(String[] args) {
        System.out.println("IMeasurable Interface");
    }
}
