package com.app.quantityMeasurement.controller;

import com.app.quantityMeasurement.entity.QuantityDTO;
import com.app.quantityMeasurement.exception.QuantityMeasurementException;
import com.app.quantityMeasurement.service.IQuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementController {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    QuantityMeasurementController.class);

    private final IQuantityMeasurementService
            quantityMeasurementService;

    public QuantityMeasurementController(
            IQuantityMeasurementService
                    quantityMeasurementService) {
        this.quantityMeasurementService =
                quantityMeasurementService;
        logger.info("QuantityMeasurementController " +
                "initialized.");
    }

    public boolean performComparison(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            boolean result =
                    quantityMeasurementService.compare(
                            thisQuantityDTO,
                            thatQuantityDTO);
            logger.info("{} {} == {} {} : {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnit(),
                    result ? "Equal" : "Not Equal");
            return result;
        } catch (QuantityMeasurementException e) {
            logger.error("Comparison error: {}",
                    e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error " +
                    "during comparison: {}",
                    e.getMessage());
            return false;
        }
    }

    public QuantityDTO performConversion(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityDTO result =
                    quantityMeasurementService.convert(
                            thisQuantityDTO,
                            thatQuantityDTO);
            logger.info("{} {} => {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnit(),
                    result.getValue(),
                    result.getUnit());
            return result;
        } catch (QuantityMeasurementException e) {
            logger.error("Conversion error: {}",
                    e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error " +
                    "during conversion: {}",
                    e.getMessage());
            return null;
        }
    }

    public QuantityDTO performAddition(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityDTO result =
                    quantityMeasurementService.add(
                            thisQuantityDTO,
                            thatQuantityDTO);
            logger.info("{} {} + {} {} = {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnit(),
                    result.getValue(),
                    result.getUnit());
            return result;
        } catch (QuantityMeasurementException e) {
            logger.error("Addition error: {}",
                    e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error " +
                    "during addition: {}",
                    e.getMessage());
            return null;
        }
    }

    public QuantityDTO performAddition(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO) {
        try {
            QuantityDTO result =
                    quantityMeasurementService.add(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            targetUnitDTO);
            logger.info("{} {} + {} {} = {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnit(),
                    result.getValue(),
                    result.getUnit());
            return result;
        } catch (QuantityMeasurementException e) {
            logger.error("Addition error: {}",
                    e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error " +
                    "during addition: {}",
                    e.getMessage());
            return null;
        }
    }

    public QuantityDTO performSubtraction(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            QuantityDTO result =
                    quantityMeasurementService.subtract(
                            thisQuantityDTO,
                            thatQuantityDTO);
            logger.info("{} {} - {} {} = {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnit(),
                    result.getValue(),
                    result.getUnit());
            return result;
        } catch (QuantityMeasurementException e) {
            logger.error("Subtraction error: {}",
                    e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error " +
                    "during subtraction: {}",
                    e.getMessage());
            return null;
        }
    }

    public QuantityDTO performSubtraction(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO,
            QuantityDTO targetUnitDTO) {
        try {
            QuantityDTO result =
                    quantityMeasurementService.subtract(
                            thisQuantityDTO,
                            thatQuantityDTO,
                            targetUnitDTO);
            logger.info("{} {} - {} {} = {} {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnit(),
                    result.getValue(),
                    result.getUnit());
            return result;
        } catch (QuantityMeasurementException e) {
            logger.error("Subtraction error: {}",
                    e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error " +
                    "during subtraction: {}",
                    e.getMessage());
            return null;
        }
    }

    public double performDivision(
            QuantityDTO thisQuantityDTO,
            QuantityDTO thatQuantityDTO) {
        try {
            double result =
                    quantityMeasurementService.divide(
                            thisQuantityDTO,
                            thatQuantityDTO);
            logger.info("{} {} / {} {} = {}",
                    thisQuantityDTO.getValue(),
                    thisQuantityDTO.getUnit(),
                    thatQuantityDTO.getValue(),
                    thatQuantityDTO.getUnit(),
                    result);
            return result;
        } catch (QuantityMeasurementException e) {
            logger.error("Division error: {}",
                    e.getMessage());
            return 0.0;
        } catch (Exception e) {
            logger.error("Unexpected error " +
                    "during division: {}",
                    e.getMessage());
            return 0.0;
        }
    }

    public static void main(String[] args) {
        System.out.println(
                "QuantityMeasurementController");
    }
}