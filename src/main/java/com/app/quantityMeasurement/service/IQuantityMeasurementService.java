package com.app.quantityMeasurement.service;

import com.app.quantityMeasurement.entity
        .QuantityDTO;
import com.app.quantityMeasurement.entity
        .QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    // ── Compare ───────────────────────────────────
    QuantityMeasurementDTO compareQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO);

    // ── Convert ───────────────────────────────────
    QuantityMeasurementDTO convertQuantity(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO);

    // ── Add ───────────────────────────────────────
    QuantityMeasurementDTO addQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO);

    // ── Subtract ──────────────────────────────────
    QuantityMeasurementDTO subtractQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO);

    // ── Divide ────────────────────────────────────
    QuantityMeasurementDTO divideQuantities(
            QuantityDTO thisDTO,
            QuantityDTO thatDTO);

    // ── History by operation ──────────────────────
    List<QuantityMeasurementDTO>
    getHistoryByOperation(
            String operation);

    // ── History by measurement type ───────────────
    List<QuantityMeasurementDTO>
    getHistoryByMeasurementType(
            String measurementType);

    // ── Count by operation ────────────────────────
    long getCountByOperation(String operation);

    // ── Error history ─────────────────────────────
    List<QuantityMeasurementDTO> getErrorHistory();
}