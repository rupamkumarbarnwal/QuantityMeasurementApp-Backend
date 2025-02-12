package com.app.quantityMeasurement.repository;

import com.app.quantityMeasurement.entity
        .QuantityMeasurementEntity;
import org.springframework.data.jpa.repository
        .JpaRepository;
import org.springframework.data.jpa.repository
        .Query;
import org.springframework.data.repository.query
        .Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository
        extends JpaRepository
              <  QuantityMeasurementEntity, Long> {

// ── Find by operation ─────────────────────────
List<QuantityMeasurementEntity>
findByOperation(String operation);

// ── Find by measurement type ──────────────────
List<QuantityMeasurementEntity>
findByThisMeasurementType(
        String measurementType);

// ── Find after date ───────────────────────────
List<QuantityMeasurementEntity>
findByCreatedAtAfter(
        LocalDateTime date);

// ── Find successful by operation ──────────────
@Query("SELECT q FROM " +
        "QuantityMeasurementEntity q " +
        "WHERE q.operation = :operation " +
        "AND q.isError = false")
List<QuantityMeasurementEntity>
findSuccessfulByOperation(
        @Param("operation")
        String operation);

// ── Count successful by operation ─────────────
long countByOperationAndIsErrorFalse(
        String operation);

// ── Find all errors ───────────────────────────
List<QuantityMeasurementEntity>
findByIsErrorTrue();
}