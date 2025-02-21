package com.quantitymeasurement.app.service;

import java.util.List;

import com.quantitymeasurement.app.dto.QuantityInputDTO;
import com.quantitymeasurement.app.dto.ResponseDTO;

public interface IQuantityMeasurementService {

    ResponseDTO compareQuantities(QuantityInputDTO dto);

    ResponseDTO convertQuantities(QuantityInputDTO dto);

    ResponseDTO addQuantities(QuantityInputDTO dto);

    ResponseDTO divideQuantities(QuantityInputDTO dto);

    ResponseDTO subtractQuantities(QuantityInputDTO dto);

    ResponseDTO multiplyQuantities(QuantityInputDTO dto);

    long getOperationCount(String operation);

    List<ResponseDTO> getHistoryByOperation(String operation);

    List<ResponseDTO> getHistoryByType(String type);

    List<ResponseDTO> getErrorHistory();


    // NEW: User-specific methods
    /**
     * Get operation count for a specific user
     */
    long getUserOperationCount(String userId, String operation);

    /**
     * Get history for a specific user by operation
     */
    List<ResponseDTO> getUserHistoryByOperation(String userId, String operation);

    /**
     * Get history for a specific user by type (length, weight, etc.)
     */
    List<ResponseDTO> getUserHistoryByType(String userId, String type);

    /**
     * Get all history for a specific user
     */
    List<ResponseDTO> getUserHistory(String userId);

    /**
     * Get error history for a specific user
     */
    List<ResponseDTO> getUserErrorHistory(String userId);
}
