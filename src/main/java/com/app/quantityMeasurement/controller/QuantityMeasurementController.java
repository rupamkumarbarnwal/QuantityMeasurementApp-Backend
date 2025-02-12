package com.app.quantityMeasurement.controller;

import com.app.quantityMeasurement.entity.QuantityInputDTO;
import com.app.quantityMeasurement.entity.QuantityMeasurementDTO;
import com.app.quantityMeasurement.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(
        name = "Quantity Measurements",
        description = "REST API for quantity measurement operations"
)
public class QuantityMeasurementController {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementController.class);

    @Autowired
    private IQuantityMeasurementService quantityMeasurementService;

    // ── Compare ───────────────────────────────────
    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        logger.info("POST /compare called");

        QuantityMeasurementDTO result =
                quantityMeasurementService.compareQuantities(
                        input.getThisQuantityDTO(),
                        input.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    // ── Convert ───────────────────────────────────
    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to another unit")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO input) {

        logger.info("POST /convert called");

        QuantityMeasurementDTO result =
                quantityMeasurementService.convertQuantity(
                        input.getThisQuantityDTO(),
                        input.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    // ── Add ───────────────────────────────────────
    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        logger.info("POST /add called");

        QuantityMeasurementDTO result =
                quantityMeasurementService.addQuantities(
                        input.getThisQuantityDTO(),
                        input.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    // ── Subtract ──────────────────────────────────
    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        logger.info("POST /subtract called");

        QuantityMeasurementDTO result =
                quantityMeasurementService.subtractQuantities(
                        input.getThisQuantityDTO(),
                        input.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    // ── Divide ────────────────────────────────────
    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO input) {

        logger.info("POST /divide called");

        QuantityMeasurementDTO result =
                quantityMeasurementService.divideQuantities(
                        input.getThisQuantityDTO(),
                        input.getThatQuantityDTO());

        return ResponseEntity.ok(result);
    }

    // ── History by operation ──────────────────────
    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get history by operation type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistoryByOperation(
            @PathVariable String operation) {

        logger.info("GET /history/operation/{}", operation);

        List<QuantityMeasurementDTO> result =
                quantityMeasurementService.getHistoryByOperation(operation);

        return ResponseEntity.ok(result);
    }

    // ── History by measurement type ───────────────
    @GetMapping("/history/type/{measurementType}")
    @Operation(summary = "Get history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistoryByType(
            @PathVariable String measurementType) {

        logger.info("GET /history/type/{}", measurementType);

        List<QuantityMeasurementDTO> result =
                quantityMeasurementService.getHistoryByMeasurementType(measurementType);

        return ResponseEntity.ok(result);
    }

    // ── Count by operation ────────────────────────
    @GetMapping("/count/{operation}")
    @Operation(summary = "Get count by operation type")
    public ResponseEntity<Long> getCountByOperation(
            @PathVariable String operation) {

        logger.info("GET /count/{}", operation);

        long result =
                quantityMeasurementService.getCountByOperation(operation);

        return ResponseEntity.ok(result);
    }

    // ── Error history ─────────────────────────────
    @GetMapping("/history/errored")
    @Operation(summary = "Get all errored operations")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {

        logger.info("GET /history/errored called");

        List<QuantityMeasurementDTO> result =
                quantityMeasurementService.getErrorHistory();

        return ResponseEntity.ok(result);
    }
}