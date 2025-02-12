package com.app.quantityMeasurement.service;

import com.app.quantityMeasurement.entity.QuantityDTO;
import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;
import com.app.quantityMeasurement.exception.QuantityMeasurementException;
import com.app.quantityMeasurement.repository.IQuantityMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("QuantityMeasurementServiceImpl Tests")
public class QuantityMeasurementServiceImplTest {

    @Mock
    private IQuantityMeasurementRepository repository;

    private IQuantityMeasurementService service;

    @BeforeEach
    public void setUp() {
        service = new QuantityMeasurementServiceImpl(
                repository);
    }

    // ── Compare Tests ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("compare 1 foot equals 12 inches")
    public void testCompare1FootEquals12Inches() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(service.compare(d1, d2));
        verify(repository, times(1))
                .save(any(
                        QuantityMeasurementEntity.class));
    }

    @Test
    @DisplayName("compare 1 kilogram equals 1000 grams")
    public void testCompare1KgEquals1000Grams() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO d2 = new QuantityDTO(
                1000.0, QuantityDTO.WeightUnit.GRAM);
        assertTrue(service.compare(d1, d2));
        verify(repository, times(1))
                .save(any(
                        QuantityMeasurementEntity.class));
    }

    @Test
    @DisplayName("compare 1 litre equals 1000 millilitres")
    public void testCompare1LitreEquals1000Ml() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO d2 = new QuantityDTO(
                1000.0,
                QuantityDTO.VolumeUnit.MILLILITRE);
        assertTrue(service.compare(d1, d2));
    }

    @Test
    @DisplayName("compare 100 Celsius equals 212 Fahrenheit")
    public void testCompare100CelsiusEquals212F() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                212.0,
                QuantityDTO.TemperatureUnit.FAHRENHEIT);
        assertTrue(service.compare(d1, d2));
    }

    @Test
    @DisplayName("compare cross category throws exception")
    public void testCompareCrossCategoryThrows() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                1.0, QuantityDTO.WeightUnit.KILOGRAM);
        assertThrows(
                QuantityMeasurementException.class,
                () -> service.compare(d1, d2));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("compare null throws exception")
    public void testCompareNullThrows() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        assertThrows(
                QuantityMeasurementException.class,
                () -> service.compare(d1, null));
    }

    // ── Convert Tests ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("convert 1 foot to inches returns 12")
    public void testConvert1FootToInches() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.convert(d1, d2);
        assertEquals(12.0, result.getValue(), 1e-6);
        assertEquals("INCHES", result.getUnitName());
        verify(repository, times(1))
                .save(any(
                        QuantityMeasurementEntity.class));
    }

    @Test
    @DisplayName("convert 100 Celsius to Fahrenheit " +
            "returns 212")
    public void testConvert100CelsiusTo212F() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                0.0,
                QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTO result = service.convert(d1, d2);
        assertEquals(212.0, result.getValue(), 1e-6);
        assertEquals("FAHRENHEIT",
                result.getUnitName());
    }

    // ── Add Tests ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("add 1 foot and 12 inches returns 2 feet")
    public void testAdd1FootAnd12Inches() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.add(d1, d2);
        assertEquals(2.0, result.getValue(), 1e-6);
        assertEquals("FEET", result.getUnitName());
        verify(repository, times(1))
                .save(any(
                        QuantityMeasurementEntity.class));
    }

    @Test
    @DisplayName("add temperature throws exception")
    public void testAddTemperatureThrows() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                50.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(
                QuantityMeasurementException.class,
                () -> service.add(d1, d2));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("add with target unit returns result " +
            "in target unit")
    public void testAddWithTargetUnit() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result =
                service.add(d1, d2, target);
        assertEquals(24.0, result.getValue(), 1e-6);
        assertEquals("INCHES", result.getUnitName());
    }

    // ── Subtract Tests ────────────────────────────────────────────────────────

    @Test
    @DisplayName("subtract 12 inches from 2 feet " +
            "returns 1 foot")
    public void testSubtract12InchesFrom2Feet() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.subtract(d1, d2);
        assertEquals(1.0, result.getValue(), 1e-6);
        assertEquals("FEET", result.getUnitName());
        verify(repository, times(1))
                .save(any(
                        QuantityMeasurementEntity.class));
    }

    @Test
    @DisplayName("subtract temperature throws exception")
    public void testSubtractTemperatureThrows() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                50.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(
                QuantityMeasurementException.class,
                () -> service.subtract(d1, d2));
    }

    // ── Divide Tests ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("divide 2 feet by 1 foot returns 2")
    public void testDivide2FeetBy1Foot() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        assertEquals(2.0,
                service.divide(d1, d2), 1e-6);
        verify(repository, times(1))
                .save(any(
                        QuantityMeasurementEntity.class));
    }

    @Test
    @DisplayName("divide by zero throws exception")
    public void testDivideByZeroThrows() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.FEET);
        assertThrows(
                QuantityMeasurementException.class,
                () -> service.divide(d1, d2));
    }

    @Test
    @DisplayName("divide temperature throws exception")
    public void testDivideTemperatureThrows() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                50.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(
                QuantityMeasurementException.class,
                () -> service.divide(d1, d2));
    }

    // ── Repository Interaction Tests ──────────────────────────────────────────

    @Test
    @DisplayName("all operations save to repository")
    public void testAllOperationsSaveToRepository() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO d3 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.INCHES);

        service.compare(d1, d2);
        service.convert(d1, d3);
        service.add(d1, d2);
        service.subtract(
                new QuantityDTO(2.0,
                        QuantityDTO.LengthUnit.FEET),
                d2);
        service.divide(d1, d2);

        verify(repository, times(5))
                .save(any(
                        QuantityMeasurementEntity.class));
    }

    @Test
    @DisplayName("repository save called with " +
            "correct operation")
    public void testRepositorySaveCalledWithCorrectOperation() {
        List<QuantityMeasurementEntity> savedEntities =
                new ArrayList<>();
        doAnswer(invocation -> {
            savedEntities.add(
                    invocation.getArgument(0));
            return null;
        }).when(repository).save(
                any(QuantityMeasurementEntity.class));

        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        service.compare(d1, d2);

        assertEquals(1, savedEntities.size());
        assertEquals("COMPARE",
                savedEntities.get(0).operation);
    }
}