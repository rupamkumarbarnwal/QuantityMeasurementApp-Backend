package quantityMeasurement.model;

import java.util.Objects;

import quantityMeasurement.IMeasurable;

public class QuantityMeasurementEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public double thisValue;
    public String thisUnit;
    public String thisMeasurementType;

    public double thatValue;
    public String thatUnit;
    public String thatMeasurementType;

    public String operation;


    public double resultValue;
    public String resultUnit;
    public String resultMeasurementType;
    public String resultString;
    public boolean isError;
    public String errorMessage;

    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation,
            String result) {
        this(thisQuantity, thatQuantity, operation);
        this.resultString = result;
        this.isError = false;
    }


    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation,
            QuantityModel<IMeasurable> result) {
        this(thisQuantity, thatQuantity, operation);
        this.resultValue           = result.getValue();
        this.resultUnit            = result.getUnit().getUnitName();
        this.resultMeasurementType = result.getUnit().getMeasurementType();
        this.isError = false;
    }

    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation,
            double result) {
        this(thisQuantity, thatQuantity, operation);
        this.resultValue = result;
        this.isError = false;
    }


    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation,
            String errorMessage,
            boolean isError) {
        this(thisQuantity, thatQuantity, operation);
        this.errorMessage = errorMessage;
        this.isError = isError;
    }


    private QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation) {
        this.thisValue           = thisQuantity.getValue();
        this.thisUnit            = thisQuantity.getUnit().getUnitName();
        this.thisMeasurementType = thisQuantity.getUnit().getMeasurementType();

        this.thatValue           = thatQuantity.getValue();
        this.thatUnit            = thatQuantity.getUnit().getUnitName();
        this.thatMeasurementType = thatQuantity.getUnit().getMeasurementType();

        this.operation = operation;
    }

 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityMeasurementEntity)) return false;
        QuantityMeasurementEntity other = (QuantityMeasurementEntity) obj;
        return Double.compare(thisValue,   other.thisValue)   == 0 &&
               Double.compare(thatValue,   other.thatValue)   == 0 &&
               Double.compare(resultValue, other.resultValue) == 0 &&
               Objects.equals(thisUnit,            other.thisUnit)            &&
               Objects.equals(thisMeasurementType, other.thisMeasurementType) &&
               Objects.equals(thatUnit,            other.thatUnit)            &&
               Objects.equals(thatMeasurementType, other.thatMeasurementType) &&
               Objects.equals(operation,           other.operation)           &&
               Objects.equals(resultUnit,          other.resultUnit)          &&
               Objects.equals(resultMeasurementType, other.resultMeasurementType) &&
               Objects.equals(resultString,        other.resultString)        &&
               Objects.equals(errorMessage,        other.errorMessage)        &&
               isError == other.isError;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(
                thisValue, thisUnit, thisMeasurementType,
                thatValue, thatUnit, thatMeasurementType,
                operation,
                resultValue, resultUnit, resultMeasurementType,
                resultString, isError, errorMessage);
    }


    @Override
    public String toString() {
        if (isError) {
            return String.format(
                    "[%s] %s %s %s %s => ERROR: %s",
                    operation,
                    thisValue,  thisUnit,
                    thatValue,  thatUnit,
                    errorMessage);
        }
        if (resultString != null) {
            return String.format(
                    "[%s] %s %s %s %s => %s",
                    operation,
                    thisValue, thisUnit,
                    thatValue, thatUnit,
                    resultString);
        }
        return String.format(
                "[%s] %s %s %s %s => %s %s",
                operation,
                thisValue,   thisUnit,
                thatValue,   thatUnit,
                resultValue, resultUnit);
    }


    public static void main(String[] args) {
        System.out.println("QuantityMeasurementEntity class");
    }
}