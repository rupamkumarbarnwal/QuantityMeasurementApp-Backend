package com.app.quantityMeasurement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation
        .FieldError;
import org.springframework.web.bind.annotation
        .ControllerAdvice;
import org.springframework.web.bind.annotation
        .ExceptionHandler;
import org.springframework.web.bind
        .MethodArgumentNotValidException;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ── Validation Exception ──────────────────────

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>>
    handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String errorMessage = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Validation failed");

        Map<String, Object> response =
                buildResponse(
                        HttpStatus.BAD_REQUEST
                                .value(),
                        "Quantity Measurement Error",
                        errorMessage,
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ── QuantityMeasurementException ──────────────

    @ExceptionHandler(
            QuantityMeasurementException.class)
    public ResponseEntity<Map<String, Object>>
    handleQuantityException(
            QuantityMeasurementException ex,
            HttpServletRequest request) {

        Map<String, Object> response =
                buildResponse(
                        HttpStatus.BAD_REQUEST
                                .value(),
                        "Quantity Measurement Error",
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ── Global Exception ──────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>>
    handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        Map<String, Object> response =
                buildResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR
                                .value(),
                        "Internal Server Error",
                        ex.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus
                        .INTERNAL_SERVER_ERROR)
                .body(response);
    }

    // ── Helper ────────────────────────────────────

    private Map<String, Object> buildResponse(
            int status,
            String error,
            String message,
            String path) {

        Map<String, Object> response =
                new HashMap<>();

        response.put("timestamp",
                LocalDateTime.now().toString());
        response.put("status",  status);
        response.put("error",   error);
        response.put("message", message);
        response.put("path",    path);

        return response;
    }
}