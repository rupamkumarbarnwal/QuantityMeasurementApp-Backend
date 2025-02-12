package com.app.quantityMeasurement.entity;

import com.app.quantityMeasurement.unit.LengthUnit;
import com.app.quantityMeasurement.unit.WeightUnit;
import com.app.quantityMeasurement.unit.TemperatureUnit;
import com.app.quantityMeasurement.unit.IMeasurable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QuantityMeasurementEntity Tests")
public class QuantityMeasurementEntityTest {

    // ── No-arg Constructor ────────────────────────────────────────────────────

    @Test
    @DisplayName("No-arg constructor creates entity " +
            "with createdAt set")
    public void testNoArgConstructor() {
        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();
        assertNotNull(entity.createdAt);
        assertFalse(entity.isError);
    }

    // ── String Result Constructor ─────────────────────────────────────────────

    @Test
    @DisplayName("String result constructor stores " +
            "compare result correctly")
    public void testStringResultConstructor() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(1.0, LengthUnit.FEET);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(12.0,
                        LengthUnit.INCHES);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        m1, m2, "COMPARE", "Equal");

        assertEquals("COMPARE",    entity.operation);
        assertEquals("Equal",      entity.resultString);
        assertEquals(1.0,          entity.thisValue);
        assertEquals("FEET",       entity.thisUnit);
        assertEquals("LengthUnit",
                entity.thisMeasurementType);
        assertEquals(12.0,         entity.thatValue);
        assertEquals("INCHES",     entity.thatUnit);
        assertFalse(entity.isError);
        assertNotNull(entity.createdAt);
    }

    // ── QuantityModel Result Constructor ─────────────────────────────────────

    @Test
    @DisplayName("QuantityModel result constructor stores " +
            "add result correctly")
    public void testQuantityModelResultConstructor() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(1.0, LengthUnit.FEET);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(12.0,
                        LengthUnit.INCHES);
        QuantityModel<IMeasurable> result =
                new QuantityModel<>(2.0, LengthUnit.FEET);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        m1, m2, "ADD", result);

        assertEquals("ADD",        entity.operation);
        assertEquals(2.0,
                entity.resultValue, 1e-6);
        assertEquals("FEET",       entity.resultUnit);
        assertEquals("LengthUnit",
                entity.resultMeasurementType);
        assertFalse(entity.isError);
    }

    // ── Double Result Constructor ─────────────────────────────────────────────

    @Test
    @DisplayName("Double result constructor stores " +
            "divide result correctly")
    public void testDoubleResultConstructor() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(2.0, LengthUnit.FEET);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(1.0, LengthUnit.FEET);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        m1, m2, "DIVIDE", 2.0);

        assertEquals("DIVIDE", entity.operation);
        assertEquals(2.0,
                entity.resultValue, 1e-6);
        assertFalse(entity.isError);
    }

    // ── Error Constructor ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Error constructor stores error correctly")
    public void testErrorConstructor() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(100.0,
                        TemperatureUnit.CELSIUS);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(50.0,
                        TemperatureUnit.CELSIUS);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        m1, m2, "ADD",
                        "Arithmetic not supported " +
                        "for TemperatureUnit",
                        true);

        assertEquals("ADD",   entity.operation);
        assertTrue(entity.isError);
        assertNotNull(entity.errorMessage);
        assertTrue(entity.errorMessage.contains(
                "TemperatureUnit"));
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("All getters return correct values")
    public void testAllGetters() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(1.0, LengthUnit.FEET);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(12.0,
                        LengthUnit.INCHES);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        m1, m2, "COMPARE", "Equal");

        assertEquals(1.0,
                entity.getThisValue(), 1e-6);
        assertEquals("FEET",
                entity.getThisUnit());
        assertEquals("LengthUnit",
                entity.getThisMeasurementType());
        assertEquals(12.0,
                entity.getThatValue(), 1e-6);
        assertEquals("INCHES",
                entity.getThatUnit());
        assertEquals("LengthUnit",
                entity.getThatMeasurementType());
        assertEquals("COMPARE",
                entity.getOperation());
        assertEquals("Equal",
                entity.getResultString());
        assertFalse(entity.isError());
        assertNull(entity.getErrorMessage());
        assertNotNull(entity.getCreatedAt());
    }

    // ── setCreatedAt ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("setCreatedAt updates createdAt field")
    public void testSetCreatedAt() {
        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();
        LocalDateTime newTime =
                LocalDateTime.of(2024, 1, 1, 0, 0);
        entity.setCreatedAt(newTime);
        assertEquals(newTime, entity.getCreatedAt());
    }

    // ── toString ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("toString contains operation and " +
            "result for compare")
    public void testToStringCompare() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(1.0, LengthUnit.FEET);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(12.0,
                        LengthUnit.INCHES);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        m1, m2, "COMPARE", "Equal");

        String str = entity.toString();
        assertTrue(str.contains("COMPARE"));
        assertTrue(str.contains("Equal"));
    }

    @Test
    @DisplayName("toString contains isError=true " +
            "for error entity")
    public void testToStringError() {
        QuantityModel<IMeasurable> m1 =
                new QuantityModel<>(100.0,
                        TemperatureUnit.CELSIUS);
        QuantityModel<IMeasurable> m2 =
                new QuantityModel<>(50.0,
                        TemperatureUnit.CELSIUS);

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        m1, m2, "ADD",
                        "Unsupported operation", true);

        String str = entity.toString();
        assertTrue(str.contains("isError=true"));
        assertTrue(str.contains(
                "Unsupported operation"));
    }
}