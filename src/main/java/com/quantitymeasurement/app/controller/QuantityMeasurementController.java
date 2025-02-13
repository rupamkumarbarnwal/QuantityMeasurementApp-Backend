package com.quantitymeasurement.app.controller;

import com.quantitymeasurement.app.dto.QuantityInputDTO;
import com.quantitymeasurement.app.dto.ResponseDTO;
import com.quantitymeasurement.app.service.IQuantityMeasurementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    public ResponseDTO compare(@RequestBody QuantityInputDTO dto) {
        return service.compareQuantities(dto);
    }

    @PostMapping("/convert")
    public ResponseDTO convert(@RequestBody QuantityInputDTO dto) {
        return service.convertQuantities(dto);
    }

    @PostMapping("/add")
    public ResponseDTO add(@RequestBody QuantityInputDTO dto) {
        return service.addQuantities(dto);
    }

    @PostMapping("/divide")
    public ResponseDTO divide(@RequestBody QuantityInputDTO dto) {
        return service.divideQuantities(dto);
    }

    @GetMapping("/count/{operation}")
    public long getCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }

    @GetMapping("/history/operation/{operation}")
    public List<ResponseDTO> getHistoryByOperation(@PathVariable String operation) {
        return service.getHistoryByOperation(operation);
    }

    @GetMapping("/history/type/{type}")
    public List<ResponseDTO> getHistoryByType(@PathVariable String type) {
        return service.getHistoryByType(type);
    }

    @GetMapping("/history/errored")
    public List<ResponseDTO> getErroredHistory() {
        return service.getErrorHistory();
    }
}