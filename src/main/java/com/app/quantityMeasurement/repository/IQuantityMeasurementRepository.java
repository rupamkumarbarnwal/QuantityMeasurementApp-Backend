package com.app.quantityMeasurement.repository;

import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> getAllMeasurements();

    List<QuantityMeasurementEntity> getMeasurementsByOperation(
            String operation);

    List<QuantityMeasurementEntity> getMeasurementsByType(
            String measurementType);

    int getTotalCount();

    void deleteAll();

    default String getPoolStatistics() {
        return "Pool statistics not available " +
                "for this repository implementation.";
    }

    default void releaseResources() {
        // default no-op
        // override in implementations that manage resources
    }

    public static void main(String[] args) {
        System.out.println("IQuantityMeasurementRepository");
    }
}