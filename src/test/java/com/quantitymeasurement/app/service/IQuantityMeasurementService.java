package com.quantitymeasurement.app.service;

import java.util.List;

import com.quantitymeasurement.app.dto.QuantityInputDTO;
import com.quantitymeasurement.app.dto.ResponseDTO;

public interface IQuantityMeasurementService {

    ResponseDTO compareQuantities(QuantityInputDTO dto);

    ResponseDTO convertQuantities(QuantityInputDTO dto);

    ResponseDTO addQuantities(QuantityInputDTO dto);

    ResponseDTO divideQuantities(QuantityInputDTO dto);

    long getOperationCount(String operation);

    List<ResponseDTO> getHistoryByOperation(String operation);

    List<ResponseDTO> getHistoryByType(String type);

    List<ResponseDTO> getErrorHistory();
}