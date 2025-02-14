package com.quantitymeasurement.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quantitymeasurement.app.entity.QuantityMeasurementEntity;

import java.util.List;

public interface QuantityRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    long countByOperationIgnoreCase(String operation);

    List<QuantityMeasurementEntity> findByOperationIgnoreCase(String operation);

    List<QuantityMeasurementEntity> findAll();
}