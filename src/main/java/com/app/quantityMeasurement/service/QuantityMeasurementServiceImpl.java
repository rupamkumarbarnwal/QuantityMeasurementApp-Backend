package com.app.quantityMeasurement.service;

import com.app.quantityMeasurement.entity.QuantityDTO;
import com.app.quantityMeasurement.entity.QuantityModel;
import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;
import com.app.quantityMeasurement.exception.QuantityMeasurementException;
import com.app.quantityMeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantityMeasurement.unit.IMeasurable;
import com.app.quantityMeasurement.unit.LengthUnit;
import com.app.quantityMeasurement.unit.WeightUnit;
import com.app.quantityMeasurement.unit.VolumeUnit;
import com.app.quantityMeasurement.unit.TemperatureUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.DoubleBinaryOperator;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(
            IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialized " +
                "with repository: {}",
                repository.getClass().getSimpleName());
    }

    private enum Operation {
        COMPARISON, CONVERSION, ARITHMETIC
    }

    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (b == 0.0)
                throw new ArithmeticException(
                        "Cannot divide by zero");
            return a / b;
        });

        private final DoubleBinaryOperator operation;

        ArithmeticOperation(DoubleBinaryOperator operation) {
            this.operation = operation;
        }

        public double compute(double a, double b) {
            return operation.applyAsDouble(a, b);
        }
    }

    private QuantityModel<IMeasurable> getQuantityModel(
            QuantityDTO quantity) {
        if (quantity == null)
            throw new IllegalArgumentException(
                    "Quantity cannot be null");
        if (quantity.getUnitName() == null ||
                quantity.getMeasurementType() == null)
            throw new IllegalArgumentException(
                    "Quantity fields cannot be null");
        IMeasurable unit = resolveUnit(
                quantity.getMeasurementType(),
                quantity.getUnitName());
        return new QuantityModel<>(quantity.getValue(), unit);
    }

    private IMeasurable resolveUnit(
            String measurementType,
            String unitName) {
        switch (measurementType.toLowerCase()) {
            case "lengthunit":
                return LengthUnit.FEET
                        .getUnitInstance(unitName);
            case "weightunit":
                return WeightUnit.GRAM
                        .getUnitInstance(unitName);
            case "volumeunit":
                return VolumeUnit.LITRE
                        .getUnitInstance(unitName);
            case "temperatureunit":
                return TemperatureUnit.CELSIUS
                        .getUnitInstance(unitName);
            default:
                throw new QuantityMeasurementException(
                        "Unknown measurement type: " +
                        measurementType);
        }
    }

    private <U extends IMeasurable> void
            validateArithmeticOperands(
                    QuantityModel<U> thisModel,
                    QuantityModel<U> thatModel,
                    QuantityModel<U> targetUnitModel,
                    boolean targetUnitRequired) {
        if (thisModel == null || thatModel == null)
            throw new IllegalArgumentException(
                    "Operand quantities cannot be null");
        if (thisModel.getUnit().getClass() !=
                thatModel.getUnit().getClass())
            throw new IllegalArgumentException(
                    "Incompatible unit categories");
        if (!Double.isFinite(thisModel.getValue()))
            throw new IllegalArgumentException(
                    "Invalid numeric value in " +
                    "first quantity");
        if (!Double.isFinite(thatModel.getValue()))
            throw new IllegalArgumentException(
                    "Invalid numeric value in " +
                    "second quantity");
        if (targetUnitRequired &&
                targetUnitModel == null)
            throw new NullPointerException(
                    "Target unit cannot be null");
    }

    private <U extends IMeasurable> double
            performArithmetic(
                    QuantityModel<U> thisModel,
                    QuantityModel<U> thatModel,
                    ArithmeticOperation operation) {
        thisModel.getUnit()
                .validateOperationSupport(
                        operation.name());
        double base1 = thisModel.getUnit()
                .convertToBaseUnit(thisModel.getValue());
        double base2 = thatModel.getUnit()
                .convertToBaseUnit(thatModel.getValue());
        return operation.compute(base1, base2);
    }

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> double
            convertTemperatureUnit(
                    QuantityModel<U> thisModel,
                    QuantityModel<U> thatModel) {
        TemperatureUnit srcUnit =
                (TemperatureUnit) thisModel.getUnit();
        TemperatureUnit targetUnit =
                (TemperatureUnit) thatModel.getUnit();
        return srcUnit.convertTo(
                thisModel.getValue(), targetUnit);
    }

    private <U extends IMeasurable> double convertTo(
            QuantityModel<U> srcModel,
            QuantityModel<U> targetModel) {
        double base = srcModel.getUnit()
                .convertToBaseUnit(srcModel.getValue());
        return targetModel.getUnit()
                .convertFromBaseUnit(base);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean compare(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityModel<IMeasurable> m1 =
                    getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> m2 =
                    getQuantityModel(thatQuantityDTO);

            if (m1.getUnit().getClass() !=
                    m2.getUnit().getClass())
                throw new QuantityMeasurementException(
                        "Incompatible unit categories");

            boolean result = compareModels(m1, m2);
            logger.debug("Compare: {} {} == {} {} => {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnitName(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnitName(),
                    result);
            return result;

        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> boolean compareModels(
            QuantityModel<U> m1,
            QuantityModel<U> m2) {
        double base1 = m1.getUnit()
                .convertToBaseUnit(m1.getValue());
        double base2 = m2.getUnit()
                .convertToBaseUnit(m2.getValue());
        boolean result =
                Math.abs(base1 - base2) < 1e-6;

        repository.save(new QuantityMeasurementEntity(
                (QuantityModel<IMeasurable>) m1,
                (QuantityModel<IMeasurable>) m2,
                "COMPARE",
                result ? "Equal" : "Not Equal"));
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO convert(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityModel<IMeasurable> srcModel =
                    getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> targetModel =
                    getQuantityModel(thatQuantityDTO);

            double convertedValue;
            if (srcModel.getUnit()
                    instanceof TemperatureUnit) {
                convertedValue = convertTemperatureUnit(
                        srcModel, targetModel);
            } else {
                convertedValue = convertTo(
                        srcModel, targetModel);
            }

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<>(
                            convertedValue,
                            targetModel.getUnit());

            repository.save(new QuantityMeasurementEntity(
                    srcModel,
                    targetModel,
                    "CONVERT",
                    resultModel));

            logger.debug("Convert: {} {} => {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnitName(),
                    convertedValue,
                    targetModel.getUnit().getUnitName());

            return new QuantityDTO(
                    convertedValue,
                    targetModel.getUnit().getUnitName(),
                    targetModel.getUnit()
                            .getMeasurementType());

        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO add(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        return add(thisQuantityDTO,
                thatQuantityDTO,
                thisQuantityDTO);
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO add(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO) {
        try {
            QuantityModel<IMeasurable> m1 =
                    getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> m2 =
                    getQuantityModel(thatQuantityDTO);
            QuantityModel<IMeasurable> target =
                    getQuantityModel(targetUnitDTO);

            validateArithmeticOperands(
                    m1, m2, target, true);

            double baseResult = performArithmetic(
                    m1, m2, ArithmeticOperation.ADD);
            double resultValue = target.getUnit()
                    .convertFromBaseUnit(baseResult);

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<>(
                            resultValue,
                            target.getUnit());

            repository.save(new QuantityMeasurementEntity(
                    m1, m2, "ADD", resultModel));

            logger.debug("Add: {} {} + {} {} = {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnitName(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnitName(),
                    resultValue,
                    target.getUnit().getUnitName());

            return new QuantityDTO(
                    resultValue,
                    target.getUnit().getUnitName(),
                    target.getUnit()
                            .getMeasurementType());

        } catch (UnsupportedOperationException e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO subtract(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        return subtract(thisQuantityDTO,
                thatQuantityDTO,
                thisQuantityDTO);
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO subtract(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO) {
        try {
            QuantityModel<IMeasurable> m1 =
                    getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> m2 =
                    getQuantityModel(thatQuantityDTO);
            QuantityModel<IMeasurable> target =
                    getQuantityModel(targetUnitDTO);

            validateArithmeticOperands(
                    m1, m2, target, true);

            double baseResult = performArithmetic(
                    m1, m2,
                    ArithmeticOperation.SUBTRACT);
            double resultValue = target.getUnit()
                    .convertFromBaseUnit(baseResult);

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<>(
                            resultValue,
                            target.getUnit());

            repository.save(new QuantityMeasurementEntity(
                    m1, m2, "SUBTRACT", resultModel));

            logger.debug(
                    "Subtract: {} {} - {} {} = {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnitName(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnitName(),
                    resultValue,
                    target.getUnit().getUnitName());

            return new QuantityDTO(
                    resultValue,
                    target.getUnit().getUnitName(),
                    target.getUnit()
                            .getMeasurementType());

        } catch (UnsupportedOperationException e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public double divide(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityModel<IMeasurable> m1 =
                    getQuantityModel(thisQuantityDTO);
            QuantityModel<IMeasurable> m2 =
                    getQuantityModel(thatQuantityDTO);

            validateArithmeticOperands(
                    m1, m2, null, false);

            double result = performArithmetic(
                    m1, m2,
                    ArithmeticOperation.DIVIDE);

            repository.save(new QuantityMeasurementEntity(
                    m1, m2, "DIVIDE", result));

            logger.debug("Divide: {} {} / {} {} = {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnitName(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnitName(),
                    result);

            return result;

        } catch (UnsupportedOperationException |
                 ArithmeticException e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        } catch (QuantityMeasurementException e) {
            throw e;
        } catch (Exception e) {
            throw new QuantityMeasurementException(
                    e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "QuantityMeasurementServiceImpl");
    }
}