package com.quantitymeasurement.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.quantitymeasurement.app.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;

    private List<QuantityMeasurementEntity> cache = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {}

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        cache.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return cache;
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }

    @Override
    public void initializeDatabase() {
        // No-op for cache repository
    }
}