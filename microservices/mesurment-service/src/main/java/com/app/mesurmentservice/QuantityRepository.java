package com.app.mesurmentservice;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Original methods
    long countByOperationIgnoreCase(String operation);

    List<QuantityMeasurementEntity> findByOperationIgnoreCase(String operation);

    List<QuantityMeasurementEntity> findAll();


    // NEW: User-specific methods for history filtering
    /**
     * Get history count for a specific user by operation
     */
    long countByOperationIgnoreCaseAndUserId(String operation, String userId);

    /**
     * Get history for a specific user by operation
     */
    List<QuantityMeasurementEntity> findByOperationIgnoreCaseAndUserId(String operation, String userId);

    /**
     * Get all history for a specific user
     */
    List<QuantityMeasurementEntity> findByUserId(String userId);

    /**
     * Get all history for a specific user ordered by timestamp (newest first)
     */
    List<QuantityMeasurementEntity> findByUserIdOrderByTimestampDesc(String userId);

    /**
     * Get history for a specific user and operation ordered by timestamp
     */
    List<QuantityMeasurementEntity> findByUserIdAndOperationIgnoreCaseOrderByTimestampDesc(String userId, String operation);
}
