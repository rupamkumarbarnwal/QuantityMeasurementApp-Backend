package com.quantitymeasurement.app.controller;

import com.quantitymeasurement.app.dto.QuantityInputDTO;
import com.quantitymeasurement.app.dto.ResponseDTO;
import com.quantitymeasurement.app.service.IQuantityMeasurementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // Helper method to get current user ID from authentication context
    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName(); // Returns email/username
        }
        return null;
    }

    @GetMapping("/home")
    public void getHome() {
        System.out.println("getHome");
    }

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

    @PostMapping("/subtract")
    public ResponseDTO subtract(@RequestBody QuantityInputDTO dto) {
        return service.subtractQuantities(dto);
    }

    @PostMapping("/multiply")
    public ResponseDTO multiply(@RequestBody QuantityInputDTO dto) {
        return service.multiplyQuantities(dto);
    }

    @GetMapping("/count/{operation}")
    public long getCount(@PathVariable String operation) {
        return service.getOperationCount(operation);
    }

    // ========== ORIGINAL ENDPOINTS (Show all users' history) ==========

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


    // ========== NEW ENDPOINTS (Show only current user's history) ==========

    /**
     * Get operation count for current user
     * GET /api/v1/quantities/user/count/{operation}
     */
    @GetMapping("/user/count/{operation}")
    public long getUserOperationCount(@PathVariable String operation) {
        String userId = getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return service.getUserOperationCount(userId, operation);
    }

    /**
     * Get history for current user by operation
     * GET /api/v1/quantities/user/history/operation/{operation}
     */
    @GetMapping("/user/history/operation/{operation}")
    public List<ResponseDTO> getUserHistoryByOperation(@PathVariable String operation) {
        String userId = getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return service.getUserHistoryByOperation(userId, operation);
    }

    /**
     * Get history for current user by type (length, weight, etc.)
     * GET /api/v1/quantities/user/history/type/{type}
     */
    @GetMapping("/user/history/type/{type}")
    public List<ResponseDTO> getUserHistoryByType(@PathVariable String type) {
        String userId = getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return service.getUserHistoryByType(userId, type);
    }

    /**
     * Get all history for current user
     * GET /api/v1/quantities/user/history
     */
    @GetMapping("/user/history")
    public List<ResponseDTO> getUserHistory() {
        String userId = getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return service.getUserHistory(userId);
    }

    /**
     * Get error history for current user
     * GET /api/v1/quantities/user/history/errored
     */
    @GetMapping("/user/history/errored")
    public List<ResponseDTO> getUserErroredHistory() {
        String userId = getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }
        return service.getUserErrorHistory(userId);
    }
}
