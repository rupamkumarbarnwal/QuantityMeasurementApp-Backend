package com.app.quantityMeasurement.controller;

import com.app.quantityMeasurement.entity.QuantityDTO;
import com.app.quantityMeasurement.exception.QuantityMeasurementException;
import com.app.quantityMeasurement.service.IQuantityMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuantityMeasurementController Tests")
public class QuantityMeasurementControllerTest {

    @Mock
    private IQuantityMeasurementService service;

    private QuantityMeasurementController controller;

    @BeforeEach
    public void setUp() {
        controller =
                new QuantityMeasurementController(
                        service);
    }

    // ── performComparison Tests ───────────────────────────────────────────────

    @Test
    @DisplayName("performComparison returns true " +
            "when service returns true")
    public void testPerformComparisonTrue() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        when(service.compare(d1, d2))
                .thenReturn(true);
        assertTrue(
                controller.performComparison(d1, d2));
        verify(service, times(1)).compare(d1, d2);
    }

    @Test
    @DisplayName("performComparison returns false " +
            "when service returns false")
    public void testPerformComparisonFalse() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.INCHES);
        when(service.compare(d1, d2))
                .thenReturn(false);
        assertFalse(
                controller.performComparison(d1, d2));
    }

    @Test
    @DisplayName("performComparison returns false " +
            "on exception")
    public void testPerformComparisonReturnsFalseOnError() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                1.0, QuantityDTO.WeightUnit.KILOGRAM);
        when(service.compare(d1, d2))
                .thenThrow(
                        new QuantityMeasurementException(
                                "Incompatible unit " +
                                "categories"));
        assertFalse(
                controller.performComparison(d1, d2));
    }

    // ── performConversion Tests ───────────────────────────────────────────────

    @Test
    @DisplayName("performConversion returns result DTO")
    public void testPerformConversion() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO expected = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        when(service.convert(d1, d2))
                .thenReturn(expected);
        QuantityDTO result =
                controller.performConversion(d1, d2);
        assertNotNull(result);
        assertEquals(12.0, result.getValue(), 1e-6);
        verify(service, times(1)).convert(d1, d2);
    }

    @Test
    @DisplayName("performConversion returns null " +
            "on error")
    public void testPerformConversionReturnsNullOnError() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                0.0, QuantityDTO.WeightUnit.KILOGRAM);
        when(service.convert(d1, d2))
                .thenThrow(
                        new QuantityMeasurementException(
                                "Incompatible categories"));
        assertNull(
                controller.performConversion(d1, d2));
    }

    // ── performAddition Tests ─────────────────────────────────────────────────

    @Test
    @DisplayName("performAddition 2-arg returns result")
    public void testPerformAddition2Arg() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO expected = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        when(service.add(d1, d2))
                .thenReturn(expected);
        QuantityDTO result =
                controller.performAddition(d1, d2);
        assertNotNull(result);
        assertEquals(2.0, result.getValue(), 1e-6);
        verify(service, times(1)).add(d1, d2);
    }

    @Test
    @DisplayName("performAddition 3-arg returns result")
    public void testPerformAddition3Arg() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO expected = new QuantityDTO(
                24.0, QuantityDTO.LengthUnit.INCHES);
        when(service.add(d1, d2, target))
                .thenReturn(expected);
        QuantityDTO result =
                controller.performAddition(
                        d1, d2, target);
        assertNotNull(result);
        assertEquals(24.0, result.getValue(), 1e-6);
    }

    @Test
    @DisplayName("performAddition returns null on error")
    public void testPerformAdditionReturnsNullOnError() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                50.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        when(service.add(d1, d2))
                .thenThrow(
                        new QuantityMeasurementException(
                                "Unsupported operation"));
        assertNull(
                controller.performAddition(d1, d2));
    }

    // ── performSubtraction Tests ──────────────────────────────────────────────

    @Test
    @DisplayName("performSubtraction returns result")
    public void testPerformSubtraction() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO expected = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        when(service.subtract(d1, d2))
                .thenReturn(expected);
        QuantityDTO result =
                controller.performSubtraction(d1, d2);
        assertNotNull(result);
        assertEquals(1.0, result.getValue(), 1e-6);
        verify(service, times(1)).subtract(d1, d2);
    }

    @Test
    @DisplayName("performSubtraction returns null " +
            "on error")
    public void testPerformSubtractionReturnsNullOnError() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                50.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        when(service.subtract(d1, d2))
                .thenThrow(
                        new QuantityMeasurementException(
                                "Unsupported operation"));
        assertNull(controller
                .performSubtraction(d1, d2));
    }

    // ── performDivision Tests ─────────────────────────────────────────────────

    @Test
    @DisplayName("performDivision returns result")
    public void testPerformDivision() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        when(service.divide(d1, d2))
                .thenReturn(2.0);
        assertEquals(2.0,
                controller.performDivision(d1, d2),
                1e-6);
        verify(service, times(1)).divide(d1, d2);
    }

    @Test
    @DisplayName("performDivision returns 0.0 on error")
    public void testPerformDivisionReturns0OnError() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.FEET);
        when(service.divide(d1, d2))
                .thenThrow(
                        new QuantityMeasurementException(
                                "Cannot divide by zero"));
        assertEquals(0.0,
                controller.performDivision(d1, d2),
                1e-6);
    }
}