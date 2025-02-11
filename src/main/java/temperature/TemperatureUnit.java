package temperature;
import java.util.function.Function;
import quantityMeasurement.IMeasurable;
import quantityMeasurement.SupportsArithmetic;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(false),
    FAHRENHEIT(true);

    final Function<Double, Double> FAHRENHEIT_TO_CELSIUS = (fahrenheit) -> (fahrenheit - 32) * 5 / 9;
    final Function<Double, Double> CELSIUS_TO_CELSIUS    = (celsius) -> celsius;

    Function<Double, Double> conversionValue;

    SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(boolean isFahrenheit) {
        if (isFahrenheit) {
            conversionValue = (fahrenheit) -> (fahrenheit - 32) * 5.0 / 9.0;
        } else {
            conversionValue = (celsius) -> celsius;
        }
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return conversionValue.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (this == FAHRENHEIT) {
            return baseValue * 9.0 / 5.0 + 32.0;
        }
        return baseValue;
    }

    public double convertTo(double value, TemperatureUnit targetUnit) {
        double celsius = this.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(celsius);
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        if (!supportsArithmetic.isSupported()) {
            String message = this.name() + " does not support " + operation + " operations.";
            throw new UnsupportedOperationException(message);
        }
    }

    @Override
    public String toString() {
        return "TemperatureUnit." + this.name();
    }

    public static void main(String[] args) {
        System.out.println("TemperatureUnit Enum");
        for (TemperatureUnit unit : TemperatureUnit.values()) {
            System.out.println(unit + " has conversion function to base unit: " + unit.conversionValue);
        }
        System.out.println("Does TemperatureUnit support arithmetic operations? " +
                TemperatureUnit.CELSIUS.supportsArithmetic() + " for CELSIUS, " +
                TemperatureUnit.FAHRENHEIT.supportsArithmetic() + " for FAHRENHEIT.");
    }
}