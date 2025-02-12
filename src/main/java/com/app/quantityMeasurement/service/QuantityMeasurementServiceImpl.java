package com.app.quantityMeasurement.service;

import com.app.quantityMeasurement.entity
        .QuantityDTO;
import com.app.quantityMeasurement.entity
        .QuantityMeasurementDTO;
import com.app.quantityMeasurement.entity
        .QuantityMeasurementEntity;
import com.app.quantityMeasurement.exception
        .QuantityMeasurementException;
import com.app.quantityMeasurement.quantity
        .Quantity;
import com.app.quantityMeasurement.repository
        .QuantityMeasurementRepository;
import com.app.quantityMeasurement.unit
        .IMeasurable;
import com.app.quantityMeasurement.unit
        .LengthUnit;
import com.app.quantityMeasurement.unit
        .WeightUnit;
import com.app.quantityMeasurement.unit
        .VolumeUnit;
import com.app.quantityMeasurement.unit
        .TemperatureUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory
        .annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    QuantityMeasurementServiceImpl
                            .class);

    @Autowired
    private QuantityMeasurementRepository
            repository;

    // ── Compare ───────────────────────────────────

    @Override
    public QuantityMeasurementDTO compareQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        try {
            validateDTO(thisDTO, "thisDTO");
            validateDTO(thatDTO, "thatDTO");

            Quantity<IMeasurable> thisQuantity =
                    buildQuantity(thisDTO);
            Quantity<IMeasurable> thatQuantity =
                    buildQuantity(thatDTO);

            boolean result =
                    thisQuantity.equals(thatQuantity);

            populateEntity(
                    entity, thisDTO,
                    thatDTO, "compare");
            entity.setResultString(
                    String.valueOf(result));
            entity.setError(false);

            repository.save(entity);

            logger.debug(
                    "Compare: {} {} vs {} {} = {}",
                    thisDTO.getValue(),
                    thisDTO.getUnitName(),
                    thatDTO.getValue(),
                    thatDTO.getUnitName(),
                    result);

            return QuantityMeasurementDTO
                    .fromEntity(entity);

        } catch (QuantityMeasurementException e) {
            logger.error(
                    "Compare error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "compare", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error(
                    "Compare error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "compare", e.getMessage());
            throw new QuantityMeasurementException(
                    "compare Error: " +
                            e.getMessage());
        }
    }

    // ── Convert ───────────────────────────────────

    @Override
    public QuantityMeasurementDTO convertQuantity(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        try {
            validateDTO(thisDTO, "thisDTO");
            validateDTO(thatDTO, "thatDTO");

            Quantity<IMeasurable> thisQuantity =
                    buildQuantity(thisDTO);
            IMeasurable targetUnit =
                    buildUnit(thatDTO);

            Quantity<IMeasurable> result =
                    thisQuantity.convertTo(
                            targetUnit);

            populateEntity(
                    entity, thisDTO,
                    thatDTO, "convert");
            entity.setResultValue(
                    result.getValue());
            entity.setResultUnit(
                    result.getUnit().getUnitName());
            entity.setResultMeasurementType(
                    result.getUnit()
                            .getClass()
                            .getSimpleName());
            entity.setError(false);

            repository.save(entity);

            logger.debug(
                    "Convert: {} {} to {} = {}",
                    thisDTO.getValue(),
                    thisDTO.getUnitName(),
                    thatDTO.getUnitName(),
                    result.getValue());

            return QuantityMeasurementDTO
                    .fromEntity(entity);

        } catch (QuantityMeasurementException e) {
            logger.error(
                    "Convert error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "convert", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error(
                    "Convert error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "convert", e.getMessage());
            throw new QuantityMeasurementException(
                    "convert Error: " +
                            e.getMessage());
        }
    }

    // ── Add ───────────────────────────────────────

    @Override
    public QuantityMeasurementDTO addQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        try {
            validateDTO(thisDTO, "thisDTO");
            validateDTO(thatDTO, "thatDTO");

            Quantity<IMeasurable> thisQuantity =
                    buildQuantity(thisDTO);
            Quantity<IMeasurable> thatQuantity =
                    buildQuantity(thatDTO);

            Quantity<IMeasurable> result =
                    thisQuantity.add(thatQuantity);

            populateEntity(
                    entity, thisDTO,
                    thatDTO, "add");
            entity.setResultValue(
                    result.getValue());
            entity.setResultUnit(
                    result.getUnit().getUnitName());
            entity.setResultMeasurementType(
                    result.getUnit()
                            .getClass()
                            .getSimpleName());
            entity.setError(false);

            repository.save(entity);

            logger.debug(
                    "Add: {} {} + {} {} = {}",
                    thisDTO.getValue(),
                    thisDTO.getUnitName(),
                    thatDTO.getValue(),
                    thatDTO.getUnitName(),
                    result.getValue());

            return QuantityMeasurementDTO
                    .fromEntity(entity);

        } catch (QuantityMeasurementException e) {
            logger.error(
                    "Add error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "add", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error(
                    "Add error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "add", e.getMessage());
            throw new QuantityMeasurementException(
                    "add Error: " +
                            e.getMessage());
        }
    }

    // ── Subtract ──────────────────────────────────

    @Override
    public QuantityMeasurementDTO subtractQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        try {
            validateDTO(thisDTO, "thisDTO");
            validateDTO(thatDTO, "thatDTO");

            Quantity<IMeasurable> thisQuantity =
                    buildQuantity(thisDTO);
            Quantity<IMeasurable> thatQuantity =
                    buildQuantity(thatDTO);

            Quantity<IMeasurable> result =
                    thisQuantity.subtract(
                            thatQuantity);

            populateEntity(
                    entity, thisDTO,
                    thatDTO, "subtract");
            entity.setResultValue(
                    result.getValue());
            entity.setResultUnit(
                    result.getUnit().getUnitName());
            entity.setResultMeasurementType(
                    result.getUnit()
                            .getClass()
                            .getSimpleName());
            entity.setError(false);

            repository.save(entity);

            logger.debug(
                    "Subtract: {} {} - {} {} = {}",
                    thisDTO.getValue(),
                    thisDTO.getUnitName(),
                    thatDTO.getValue(),
                    thatDTO.getUnitName(),
                    result.getValue());

            return QuantityMeasurementDTO
                    .fromEntity(entity);

        } catch (QuantityMeasurementException e) {
            logger.error(
                    "Subtract error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "subtract", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            logger.error(
                    "Subtract error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "subtract", e.getMessage());
            throw new QuantityMeasurementException(
                    "subtract Error: " +
                            e.getMessage());
        }
    }

    // ── Divide ────────────────────────────────────

    @Override
    public QuantityMeasurementDTO divideQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        try {
            validateDTO(thisDTO, "thisDTO");
            validateDTO(thatDTO, "thatDTO");

            Quantity<IMeasurable> thisQuantity =
                    buildQuantity(thisDTO);
            Quantity<IMeasurable> thatQuantity =
                    buildQuantity(thatDTO);

            double result =
                    thisQuantity.divide(
                            thatQuantity);

            populateEntity(
                    entity, thisDTO,
                    thatDTO, "divide");
            entity.setResultValue(result);
            entity.setError(false);

            repository.save(entity);

            logger.debug(
                    "Divide: {} {} / {} {} = {}",
                    thisDTO.getValue(),
                    thisDTO.getUnitName(),
                    thatDTO.getValue(),
                    thatDTO.getUnitName(),
                    result);

            return QuantityMeasurementDTO
                    .fromEntity(entity);

        } catch (QuantityMeasurementException e) {
            logger.error(
                    "Divide error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "divide", e.getMessage());
            throw e;
        } catch (ArithmeticException e) {
            logger.error(
                    "Divide error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "divide", e.getMessage());
            throw new QuantityMeasurementException(
                    e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(
                    "Divide error: {}",
                    e.getMessage());
            saveErrorEntity(
                    entity, thisDTO, thatDTO,
                    "divide", e.getMessage());
            throw new QuantityMeasurementException(
                    "divide Error: " +
                            e.getMessage());
        }
    }

    // ── History by operation ──────────────────────

    @Override
    public List<QuantityMeasurementDTO>
    getHistoryByOperation(
            String operation) {

        logger.debug(
                "History for operation: {}",
                operation);

        return QuantityMeasurementDTO
                .fromEntityList(
                        repository
                                .findByOperation(
                                        operation
                                                .toLowerCase()));
    }

    // ── History by measurement type ───────────────

    @Override
    public List<QuantityMeasurementDTO>
    getHistoryByMeasurementType(
            String measurementType) {

        logger.debug(
                "History for type: {}",
                measurementType);

        return QuantityMeasurementDTO
                .fromEntityList(
                        repository
                                .findByThisMeasurementType(
                                        measurementType));
    }

    // ── Count by operation ────────────────────────

    @Override
    public long getCountByOperation(
            String operation) {

        logger.debug(
                "Count for operation: {}",
                operation);

        return repository
                .countByOperationAndIsErrorFalse(
                        operation.toLowerCase());
    }

    // ── Error history ─────────────────────────────

    @Override
    public List<QuantityMeasurementDTO>
    getErrorHistory() {

        logger.debug("Getting error history");

        return QuantityMeasurementDTO
                .fromEntityList(
                        repository
                                .findByIsErrorTrue());
    }

    // ── Private Helpers ───────────────────────────

    private void validateDTO(
            QuantityDTO dto,
            String fieldName) {

        if (dto == null) {
            throw new QuantityMeasurementException(
                    fieldName +
                            " must not be null");
        }
        if (dto.getUnitName() == null ||
                dto.getUnitName().isEmpty()) {
            throw new QuantityMeasurementException(
                    fieldName +
                            " unit name must not be empty");
        }
        if (dto.getMeasurementType() == null ||
                dto.getMeasurementType().isEmpty()) {
            throw new QuantityMeasurementException(
                    fieldName +
                            " measurement type must " +
                            "not be empty");
        }
    }

    private void populateEntity(
            QuantityMeasurementEntity entity,
            QuantityDTO thisDTO,
            QuantityDTO thatDTO,
            String operation) {

        entity.setThisValue(
                thisDTO.getValue());
        entity.setThisUnit(
                thisDTO.getUnitName());
        entity.setThisMeasurementType(
                thisDTO.getMeasurementType());
        entity.setThatValue(
                thatDTO.getValue());
        entity.setThatUnit(
                thatDTO.getUnitName());
        entity.setThatMeasurementType(
                thatDTO.getMeasurementType());
        entity.setOperation(operation);
    }

    private void saveErrorEntity(
            QuantityMeasurementEntity entity,
            QuantityDTO thisDTO,
            QuantityDTO thatDTO,
            String operation,
            String errorMessage) {

        populateEntity(
                entity, thisDTO,
                thatDTO, operation);
        entity.setError(true);
        entity.setErrorMessage(errorMessage);
        repository.save(entity);
    }

    // ── buildQuantity ─────────────────────────────

    @SuppressWarnings("unchecked")
    private Quantity<IMeasurable> buildQuantity(
            QuantityDTO dto) {

        IMeasurable unit = buildUnit(dto);
        return new Quantity<>(
                dto.getValue(), unit);
    }

    // ── buildUnit ─────────────────────────────────

    private IMeasurable buildUnit(QuantityDTO dto) {

        String measurementType =
                dto.getMeasurementType();
        String unitName =
                dto.getUnitName();

        try {
            switch (measurementType) {

                case "LengthUnit":
                    return LengthUnit.valueOf(
                            unitName);

                case "WeightUnit":
                    return WeightUnit.valueOf(
                            unitName);

                case "VolumeUnit":
                    return VolumeUnit.valueOf(
                            unitName);

                case "TemperatureUnit":
                    return TemperatureUnit.valueOf(
                            unitName);

                default:
                    throw new
                            QuantityMeasurementException(
                            "Unknown measurement " +
                                    "type: " +
                                    measurementType);
            }

        } catch (IllegalArgumentException e) {
            throw new QuantityMeasurementException(
                    "Invalid unit: " +
                            unitName +
                            " for type: " +
                            measurementType);
        }
    }
}