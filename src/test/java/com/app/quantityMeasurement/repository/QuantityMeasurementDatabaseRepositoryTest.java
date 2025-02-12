package com.app.quantityMeasurement.repository;

import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;
import com.app.quantityMeasurement.entity.QuantityModel;
import com.app.quantityMeasurement.unit.IMeasurable;
import com.app.quantityMeasurement.unit.LengthUnit;
import com.app.quantityMeasurement.unit.WeightUnit;
import com.app.quantityMeasurement.unit.VolumeUnit;
import com.app.quantityMeasurement.unit.TemperatureUnit;
import com.app.quantityMeasurement.util.ApplicationConfig;
import com.app.quantityMeasurement.util.ConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QuantityMeasurementDatabaseRepository Tests")
public class QuantityMeasurementDatabaseRepositoryTest {

    private QuantityMeasurementDatabaseRepository repository;
    private ApplicationConfig config;
    private ConnectionPool    connectionPool;

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

    private QuantityMeasurementEntity
            createLengthCompareEntity() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(1.0,
                        LengthUnit.FEET);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(12.0,
                        LengthUnit.INCHES);
        return new QuantityMeasurementEntity(
                m1, m2, "COMPARE", "Equal");
    }

    private QuantityMeasurementEntity
            createWeightAddEntity() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(1.0,
                        WeightUnit.KILOGRAM);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(1000.0,
                        WeightUnit.GRAM);
        QuantityModel<IMeasurable> result =
                new QuantityModel<>(2.0,
                        WeightUnit.KILOGRAM);
        return new QuantityMeasurementEntity(
                m1, m2, "ADD", result);
    }

    private QuantityMeasurementEntity
            createVolumeConvertEntity() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(1.0,
                        VolumeUnit.LITRE);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(0.0,
                        VolumeUnit.MILLILITRE);
        QuantityModel<IMeasurable> result =
                new QuantityModel<>(1000.0,
                        VolumeUnit.MILLILITRE);
        return new QuantityMeasurementEntity(
                m1, m2, "CONVERT", result);
    }

    private QuantityMeasurementEntity
            createTemperatureEntity() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(100.0,
                        TemperatureUnit.CELSIUS);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(0.0,
                        TemperatureUnit.FAHRENHEIT);
        QuantityModel<IMeasurable> result =
                new QuantityModel<>(212.0,
                        TemperatureUnit.FAHRENHEIT);
        return new QuantityMeasurementEntity(
                m1, m2, "CONVERT", result);
    }

    @Test
    @DisplayName("testDatabaseRepository_SaveEntity")
    public void testDatabaseRepository_SaveEntity() {
        repository.save(createLengthCompareEntity());
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @DisplayName("save null entity is ignored")
    public void testSaveNullIgnored() {
        repository.save(null);
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    @DisplayName("testDatabaseRepository_RetrieveAllMeasurements")
    public void testDatabaseRepository_RetrieveAllMeasurements() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());
        repository.save(createVolumeConvertEntity());

        List<QuantityMeasurementEntity> all =
                repository.getAllMeasurements();
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("getAllMeasurements returns correct data")
    public void testGetAllMeasurementsData() {
        repository.save(createLengthCompareEntity());
        List<QuantityMeasurementEntity> all =
                repository.getAllMeasurements();

        assertEquals(1, all.size());
        QuantityMeasurementEntity entity =
                all.get(0);
        assertEquals("COMPARE",
                entity.operation);
        assertEquals(1.0,
                entity.thisValue, 1e-6);
        assertEquals("FEET",
                entity.thisUnit);
        assertEquals("LengthUnit",
                entity.thisMeasurementType);
        assertEquals(12.0,
                entity.thatValue, 1e-6);
        assertEquals("INCHES",
                entity.thatUnit);
        assertEquals("Equal",
                entity.resultString);
        assertFalse(entity.isError);
        assertNotNull(entity.createdAt);
    }

    @Test
    @DisplayName("testDatabaseRepository_QueryByOperation")
    public void testDatabaseRepository_QueryByOperation() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());
        repository.save(createVolumeConvertEntity());

        List<QuantityMeasurementEntity> compareList =
                repository.getMeasurementsByOperation(
                        "COMPARE");
        List<QuantityMeasurementEntity> addList =
                repository.getMeasurementsByOperation(
                        "ADD");
        List<QuantityMeasurementEntity> convertList =
                repository.getMeasurementsByOperation(
                        "CONVERT");

        assertEquals(1, compareList.size());
        assertEquals(1, addList.size());
        assertEquals(1, convertList.size());
    }

    @Test
    @DisplayName("getMeasurementsByOperation null " +
            "returns empty")
    public void testGetMeasurementsByOperationNull() {
        repository.save(createLengthCompareEntity());
        assertTrue(repository
                .getMeasurementsByOperation(null)
                .isEmpty());
    }

    @Test
    @DisplayName("testDatabaseRepository_QueryByMeasurementType")
    public void testDatabaseRepository_QueryByMeasurementType() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());
        repository.save(createTemperatureEntity());

        List<QuantityMeasurementEntity> lengthList =
                repository.getMeasurementsByType(
                        "LengthUnit");
        List<QuantityMeasurementEntity> weightList =
                repository.getMeasurementsByType(
                        "WeightUnit");
        List<QuantityMeasurementEntity> tempList =
                repository.getMeasurementsByType(
                        "TemperatureUnit");

        assertEquals(1, lengthList.size());
        assertEquals(1, weightList.size());
        assertEquals(1, tempList.size());
    }

    @Test
    @DisplayName("getMeasurementsByType null " +
            "returns empty")
    public void testGetMeasurementsByTypeNull() {
        repository.save(createLengthCompareEntity());
        assertTrue(repository
                .getMeasurementsByType(null)
                .isEmpty());
    }

    @Test
    @DisplayName("testDatabaseRepository_CountMeasurements")
    public void testDatabaseRepository_CountMeasurements() {
        assertEquals(0, repository.getTotalCount());
        repository.save(createLengthCompareEntity());
        assertEquals(1, repository.getTotalCount());
        repository.save(createWeightAddEntity());
        assertEquals(2, repository.getTotalCount());
        repository.save(createVolumeConvertEntity());
        assertEquals(3, repository.getTotalCount());
    }

    @Test
    @DisplayName("testDatabaseRepository_DeleteAll")
    public void testDatabaseRepository_DeleteAll() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());
        assertEquals(2, repository.getTotalCount());

        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    @DisplayName("testSQLInjectionPrevention")
    public void testSQLInjectionPrevention() {
        repository.save(createLengthCompareEntity());

        String injectionAttempt =
                "COMPARE'; DROP TABLE " +
                "quantity_measurement_entity; --";

        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation(
                        injectionAttempt);
        assertTrue(result.isEmpty());
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @DisplayName("testDatabaseRepositoryPoolStatistics")
    public void testDatabaseRepositoryPoolStatistics() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains(
                "ConnectionPool Statistics"));
    }

    @Test
    @DisplayName("testParameterizedQuery_DateTimeHandling")
    public void testParameterizedQuery_DateTimeHandling() {
        repository.save(createLengthCompareEntity());
        List<QuantityMeasurementEntity> all =
                repository.getAllMeasurements();
        assertEquals(1, all.size());
        assertNotNull(all.get(0).createdAt);
    }

    @Test
    @DisplayName("testDatabaseRepository_LargeDataSet")
    public void testDatabaseRepository_LargeDataSet() {
        for (int i = 0; i < 100; i++) {
            repository.save(
                    createLengthCompareEntity());
        }
        assertEquals(100, repository.getTotalCount());
        List<QuantityMeasurementEntity> all =
                repository.getAllMeasurements();
        assertEquals(100, all.size());
    }
}