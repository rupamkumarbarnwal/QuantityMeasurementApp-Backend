package com.quantitymeasurement.app.repository;

import java.util.List;

import com.quantitymeasurement.app.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> getAllMeasurements();

    void deleteAll();
    
    void initializeDatabase();
}