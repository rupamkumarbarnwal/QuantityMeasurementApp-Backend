package com.app.quantityMeasurement.repository;

import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;
import com.app.quantityMeasurement.entity.QuantityModel;
import com.app.quantityMeasurement.unit.IMeasurable;
import com.app.quantityMeasurement.unit.LengthUnit;
import com.app.quantityMeasurement.unit.WeightUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QuantityMeasurementCacheRepository Tests")
public class QuantityMeasurementCacheRepositoryTest {

    private QuantityMeasurementCacheRepository repository;

    @BeforeEach
    public void setUp() {
        QuantityMeasurementCacheRepository
                .resetInstance();
        repository =
                QuantityMeasurementCacheRepository
                        .getInstance();
        repository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
        QuantityMeasurementCacheRepository
                .resetInstance();
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

    @Test
    @DisplayName("save stores entity in cache")
    public void testSave() {
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
    @DisplayName("getAllMeasurements returns all " +
            "saved entities")
    public void testGetAllMeasurements() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());
        List<QuantityMeasurementEntity> all =
                repository.getAllMeasurements();
        assertEquals(2, all.size());
    }

    @Test
    @DisplayName("getAllMeasurements returns " +
            "defensive copy")
    public void testGetAllMeasurementsDefensiveCopy() {
        repository.save(createLengthCompareEntity());
        List<QuantityMeasurementEntity> list1 =
                repository.getAllMeasurements();
        List<QuantityMeasurementEntity> list2 =
                repository.getAllMeasurements();
        assertNotSame(list1, list2);
    }

    @Test
    @DisplayName("getMeasurementsByOperation " +
            "filters correctly")
    public void testGetMeasurementsByOperation() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());

        List<QuantityMeasurementEntity> compareList =
                repository.getMeasurementsByOperation(
                        "COMPARE");
        List<QuantityMeasurementEntity> addList =
                repository.getMeasurementsByOperation(
                        "ADD");

        assertEquals(1, compareList.size());
        assertEquals(1, addList.size());
        assertEquals("COMPARE",
                compareList.get(0).operation);
        assertEquals("ADD",
                addList.get(0).operation);
    }

    @Test
    @DisplayName("getMeasurementsByOperation " +
            "case insensitive")
    public void testGetMeasurementsByOperationCaseInsensitive() {
        repository.save(createLengthCompareEntity());
        assertEquals(1,
                repository.getMeasurementsByOperation(
                        "compare").size());
        assertEquals(1,
                repository.getMeasurementsByOperation(
                        "COMPARE").size());
    }

    @Test
    @DisplayName("getMeasurementsByOperation null " +
            "returns empty list")
    public void testGetMeasurementsByOperationNull() {
        repository.save(createLengthCompareEntity());
        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByOperation(
                        null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getMeasurementsByType " +
            "filters correctly")
    public void testGetMeasurementsByType() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());

        List<QuantityMeasurementEntity> lengthList =
                repository.getMeasurementsByType(
                        "LengthUnit");
        List<QuantityMeasurementEntity> weightList =
                repository.getMeasurementsByType(
                        "WeightUnit");

        assertEquals(1, lengthList.size());
        assertEquals(1, weightList.size());
    }

    @Test
    @DisplayName("getMeasurementsByType null " +
            "returns empty list")
    public void testGetMeasurementsByTypeNull() {
        repository.save(createLengthCompareEntity());
        List<QuantityMeasurementEntity> result =
                repository.getMeasurementsByType(null);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("getTotalCount returns correct count")
    public void testGetTotalCount() {
        assertEquals(0, repository.getTotalCount());
        repository.save(createLengthCompareEntity());
        assertEquals(1, repository.getTotalCount());
        repository.save(createWeightAddEntity());
        assertEquals(2, repository.getTotalCount());
    }

    @Test
    @DisplayName("deleteAll clears all entities")
    public void testDeleteAll() {
        repository.save(createLengthCompareEntity());
        repository.save(createWeightAddEntity());
        assertEquals(2, repository.getTotalCount());
        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    @DisplayName("getPoolStatistics returns string")
    public void testGetPoolStatistics() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains(
                "CacheRepository Statistics"));
    }

    @Test
    @DisplayName("Singleton returns same instance")
    public void testSingleton() {
        QuantityMeasurementCacheRepository r1 =
                QuantityMeasurementCacheRepository
                        .getInstance();
        QuantityMeasurementCacheRepository r2 =
                QuantityMeasurementCacheRepository
                        .getInstance();
        assertSame(r1, r2);
    }
}