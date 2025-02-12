package com.app.quantityMeasurement.integrationTests;

import com.app.quantityMeasurement.controller.QuantityMeasurementController;
import com.app.quantityMeasurement.entity.QuantityDTO;
import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;
import com.app.quantityMeasurement.exception.QuantityMeasurementException;
import com.app.quantityMeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantityMeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantityMeasurement.service.IQuantityMeasurementService;
import com.app.quantityMeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantityMeasurement.util.ApplicationConfig;
import com.app.quantityMeasurement.util.ConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Quantity Measurement Integration Tests")
public class QuantityMeasurementIntegrationTest {

    private IQuantityMeasurementRepository repository;
    private IQuantityMeasurementService    service;
    private QuantityMeasurementController  controller;
    private ApplicationConfig              config;
    private ConnectionPool                 connectionPool;

    @BeforeEach
    public void setUp() {
        System.setProperty("app.environment", "test");
        System.setProperty("app.repository.type",
                "database");
        System.setProperty("db.schema.auto", "true");
        System.setProperty("db.schema.file",
                "db/schema.sql");

        ApplicationConfig.resetInstance();
        ConnectionPool.resetInstance();

        config         = ApplicationConfig.getInstance();
        connectionPool =
                ConnectionPool.getInstance(config);
        repository     =
                new QuantityMeasurementDatabaseRepository(
                        connectionPool, config);
        service        =
                new QuantityMeasurementServiceImpl(
                        repository);
        controller     =
                new QuantityMeasurementController(
                        service);

        repository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        if (repository != null) {
            repository.deleteAll();
        }
        ConnectionPool.resetInstance();
        ApplicationConfig.resetInstance();
        System.clearProperty("app.environment");
        System.clearProperty("app.repository.type");
        System.clearProperty("db.schema.auto");
        System.clearProperty("db.schema.file");
    }

    // ── End-to-End Length Tests ───────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_EndToEnd_LengthComparison")
    public void testIntegration_EndToEnd_LengthComparison() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);

        assertTrue(
                controller.performComparison(d1, d2));

        List<QuantityMeasurementEntity> entities =
                repository.getAllMeasurements();
        assertEquals(1, entities.size());
        assertEquals("COMPARE",
                entities.get(0).operation);
        assertEquals("Equal",
                entities.get(0).resultString);
    }

    @Test
    @DisplayName("testIntegration_EndToEnd_LengthConversion")
    public void testIntegration_EndToEnd_LengthConversion() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result =
                controller.performConversion(d1, d2);

        assertNotNull(result);
        assertEquals(12.0, result.getValue(), 1e-6);
        assertEquals("INCHES", result.getUnitName());
        assertEquals(1, repository.getTotalCount());
        assertEquals("CONVERT",
                repository.getAllMeasurements()
                        .get(0).operation);
    }

    @Test
    @DisplayName("testIntegration_EndToEnd_LengthAddition")
    public void testIntegration_EndToEnd_LengthAddition() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result =
                controller.performAddition(d1, d2);

        assertNotNull(result);
        assertEquals(2.0, result.getValue(), 1e-6);
        assertEquals("FEET", result.getUnitName());
        assertEquals(1, repository.getTotalCount());
        assertEquals("ADD",
                repository.getAllMeasurements()
                        .get(0).operation);
    }

    @Test
    @DisplayName("testIntegration_EndToEnd_LengthSubtraction")
    public void testIntegration_EndToEnd_LengthSubtraction() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result =
                controller.performSubtraction(d1, d2);

        assertNotNull(result);
        assertEquals(1.0, result.getValue(), 1e-6);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @DisplayName("testIntegration_EndToEnd_LengthDivision")
    public void testIntegration_EndToEnd_LengthDivision() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);

        double result =
                controller.performDivision(d1, d2);

        assertEquals(2.0, result, 1e-6);
        assertEquals(1, repository.getTotalCount());
    }

    // ── End-to-End Weight Tests ───────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_EndToEnd_WeightComparison")
    public void testIntegration_EndToEnd_WeightComparison() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO d2 = new QuantityDTO(
                1000.0, QuantityDTO.WeightUnit.GRAM);

        assertTrue(
                controller.performComparison(d1, d2));
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @DisplayName("testIntegration_EndToEnd_WeightAddition")
    public void testIntegration_EndToEnd_WeightAddition() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO d2 = new QuantityDTO(
                1000.0, QuantityDTO.WeightUnit.GRAM);

        QuantityDTO result =
                controller.performAddition(d1, d2);

        assertNotNull(result);
        assertEquals(2.0, result.getValue(), 1e-6);
        assertEquals("KILOGRAM",
                result.getUnitName());
    }

    // ── End-to-End Temperature Tests ──────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_EndToEnd_TemperatureComparison")
    public void testIntegration_EndToEnd_TemperatureComparison() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                212.0,
                QuantityDTO.TemperatureUnit.FAHRENHEIT);

        assertTrue(
                controller.performComparison(d1, d2));
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @DisplayName("testIntegration_EndToEnd_TemperatureUnsupported")
    public void testIntegration_EndToEnd_TemperatureUnsupported() {
        QuantityDTO d1 = new QuantityDTO(
                100.0,
                QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO d2 = new QuantityDTO(
                50.0,
                QuantityDTO.TemperatureUnit.CELSIUS);

        QuantityDTO result =
                controller.performAddition(d1, d2);

        assertNull(result);
        assertEquals(0, repository.getTotalCount());
    }

    // ── Cross Category Tests ──────────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_CrossCategory_Prevention")
    public void testIntegration_CrossCategory_Prevention() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                1.0, QuantityDTO.WeightUnit.KILOGRAM);

        assertFalse(
                controller.performComparison(d1, d2));
        assertEquals(0, repository.getTotalCount());
    }

    // ── Multiple Operations Tests ─────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_MultipleOperations_Stored")
    public void testIntegration_MultipleOperations_Stored() {
        QuantityDTO l1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO l2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO l3 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.INCHES);

        controller.performComparison(l1, l2);
        controller.performConversion(l1, l3);
        controller.performAddition(l1, l2);
        controller.performSubtraction(
                new QuantityDTO(2.0,
                        QuantityDTO.LengthUnit.FEET),
                l2);
        controller.performDivision(l1, l2);

        assertEquals(5, repository.getTotalCount());
        assertEquals(1, repository
                .getMeasurementsByOperation("COMPARE")
                .size());
        assertEquals(1, repository
                .getMeasurementsByOperation("CONVERT")
                .size());
        assertEquals(1, repository
                .getMeasurementsByOperation("ADD")
                .size());
        assertEquals(1, repository
                .getMeasurementsByOperation("SUBTRACT")
                .size());
        assertEquals(1, repository
                .getMeasurementsByOperation("DIVIDE")
                .size());
    }

    // ── Repository Switch Tests ───────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_ServiceWithDatabaseRepository")
    public void testIntegration_ServiceWithDatabaseRepository() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);

        assertTrue(service.compare(d1, d2));
        assertEquals(1, repository.getTotalCount());
        assertInstanceOf(
                QuantityMeasurementDatabaseRepository.class,
                repository);
    }

    @Test
    @DisplayName("testIntegration_ServiceWithCacheRepository")
    public void testIntegration_ServiceWithCacheRepository() {
        IQuantityMeasurementRepository cacheRepo =
                new IQuantityMeasurementRepository() {
                    private final List
                            <QuantityMeasurementEntity> store = new ArrayList<>();

                    @Override
                    public void save(
                            QuantityMeasurementEntity e) {
                        store.add(e);
                    }

                    @Override
                    public List<QuantityMeasurementEntity>
                            getAllMeasurements() {
                        return new ArrayList<>(store);
                    }

                    @Override
                    public List<QuantityMeasurementEntity>
                            getMeasurementsByOperation(
                                    String op) {
                        return new ArrayList<>();
                    }

                    @Override
                    public List<QuantityMeasurementEntity>
                            getMeasurementsByType(
                                    String type) {
                        return new ArrayList<>();
                    }

                    @Override
                    public int getTotalCount() {
                        return store.size();
                    }

                    @Override
                    public void deleteAll() {
                        store.clear();
                    }
                };

        IQuantityMeasurementService cacheService =
                new QuantityMeasurementServiceImpl(
                        cacheRepo);

        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);

        assertTrue(cacheService.compare(d1, d2));
        assertEquals(1, cacheRepo.getTotalCount());
        assertEquals(0, repository.getTotalCount());
    }

    // ── Delete All Tests ──────────────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_DeleteAll_ClearsDatabase")
    public void testIntegration_DeleteAll_ClearsDatabase() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                12.0, QuantityDTO.LengthUnit.INCHES);

        controller.performComparison(d1, d2);
        controller.performAddition(d1, d2);
        assertEquals(2, repository.getTotalCount());

        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
    }

    // ── Pool Statistics Tests ─────────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_PoolStatistics")
    public void testIntegration_PoolStatistics() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains(
                "ConnectionPool Statistics"));
        assertTrue(stats.contains("Total="));
        assertTrue(stats.contains("Active="));
        assertTrue(stats.contains("Available="));
    }

    // ── Exception Handling Tests ──────────────────────────────────────────────

    @Test
    @DisplayName("testIntegration_ExceptionHandling_NullDTO")
    public void testIntegration_ExceptionHandling_NullDTO() {
        QuantityDTO d1 = new QuantityDTO(
                1.0, QuantityDTO.LengthUnit.FEET);

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.compare(d1, null));
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    @DisplayName("testIntegration_ExceptionHandling_DivideByZero")
    public void testIntegration_ExceptionHandling_DivideByZero() {
        QuantityDTO d1 = new QuantityDTO(
                2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO d2 = new QuantityDTO(
                0.0, QuantityDTO.LengthUnit.FEET);

        assertThrows(
                QuantityMeasurementException.class,
                () -> service.divide(d1, d2));
        assertEquals(0, repository.getTotalCount());
    }
}