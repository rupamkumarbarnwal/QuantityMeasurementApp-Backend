package com.app.quantityMeasurement.repository;

import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    QuantityMeasurementCacheRepository.class);

    private static final String FILE_NAME =
            "quantity_measurement_repo.ser";

    private static QuantityMeasurementCacheRepository instance;

    private List<QuantityMeasurementEntity> cache;

    private QuantityMeasurementCacheRepository() {
        cache = new ArrayList<>();
        loadFromDisk();
        logger.info("QuantityMeasurementCacheRepository initialized. " +
                "Loaded {} records from disk.", cache.size());
    }

    public static QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    @Override
    protected Object clone()
            throws CloneNotSupportedException {
        throw new CloneNotSupportedException(
                "Singleton cannot be cloned");
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        if (entity == null) {
            logger.warn("Attempted to save null entity. Skipping.");
            return;
        }
        cache.add(entity);
        saveToDisk();
        logger.debug("Saved entity. Operation: {}. " +
                "Total records: {}",
                entity.operation, cache.size());
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        logger.debug("Retrieving all measurements. " +
                "Count: {}", cache.size());
        return new ArrayList<>(cache);
    }

    @Override
    public List<QuantityMeasurementEntity>
            getMeasurementsByOperation(String operation) {
        if (operation == null) return new ArrayList<>();
        List<QuantityMeasurementEntity> result = cache.stream()
                .filter(e -> operation.equalsIgnoreCase(
                        e.operation))
                .collect(Collectors.toList());
        logger.debug("getMeasurementsByOperation({}). " +
                "Found: {}", operation, result.size());
        return result;
    }

    @Override
    public List<QuantityMeasurementEntity>
            getMeasurementsByType(String measurementType) {
        if (measurementType == null) return new ArrayList<>();
        List<QuantityMeasurementEntity> result = cache.stream()
                .filter(e ->
                        measurementType.equalsIgnoreCase(
                                e.thisMeasurementType) ||
                        measurementType.equalsIgnoreCase(
                                e.thatMeasurementType))
                .collect(Collectors.toList());
        logger.debug("getMeasurementsByType({}). " +
                "Found: {}", measurementType, result.size());
        return result;
    }

    @Override
    public int getTotalCount() {
        return cache.size();
    }

    @Override
    public void deleteAll() {
        cache.clear();
        deleteFile();
        logger.info("All measurements deleted from " +
                "cache and disk.");
    }

    @Override
    public String getPoolStatistics() {
        return String.format(
                "CacheRepository Statistics: " +
                "[TotalRecords=%d, File=%s]",
                cache.size(), FILE_NAME);
    }

    @Override
    public void releaseResources() {
        logger.info("Releasing CacheRepository resources.");
        cache.clear();
    }

    private void saveToDisk() {
        File file = new File(FILE_NAME);
        try {
            if (file.exists()) {
                try (FileOutputStream fos =
                             new FileOutputStream(file, true);
                     ObjectOutputStream oos =
                             new AppendableObjectOutputStream(fos)) {
                    oos.writeObject(
                            cache.get(cache.size() - 1));
                }
            } else {
                try (FileOutputStream fos =
                             new FileOutputStream(file);
                     ObjectOutputStream oos =
                             new ObjectOutputStream(fos)) {
                    oos.writeObject(
                            cache.get(cache.size() - 1));
                }
            }
            logger.debug("Entity saved to disk: {}", FILE_NAME);
        } catch (IOException e) {
            logger.error("Failed to save entity to disk: {}",
                    e.getMessage());
        }
    }

    private void loadFromDisk() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            logger.info("No existing data file found: {}",
                    FILE_NAME);
            return;
        }
        try (FileInputStream fis =
                     new FileInputStream(file);
             ObjectInputStream ois =
                     new ObjectInputStream(fis)) {
            while (true) {
                try {
                    QuantityMeasurementEntity entity =
                            (QuantityMeasurementEntity)
                                    ois.readObject();
                    cache.add(entity);
                } catch (EOFException e) {
                    break;
                }
            }
            logger.info("Loaded {} records from disk: {}",
                    cache.size(), FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Failed to load from disk: {}",
                    e.getMessage());
        }
    }

    private void deleteFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                logger.info("Deleted data file: {}", FILE_NAME);
            } else {
                logger.warn("Failed to delete data file: {}",
                        FILE_NAME);
            }
        }
    }

    private static class AppendableObjectOutputStream
            extends ObjectOutputStream {

        public AppendableObjectOutputStream(
                FileOutputStream fos) throws IOException {
            super(fos);
        }

        @Override
        protected void writeStreamHeader()
                throws IOException {
            reset();
        }
    }

    public static void main(String[] args) {
        System.out.println("QuantityMeasurementCacheRepository");
    }
}