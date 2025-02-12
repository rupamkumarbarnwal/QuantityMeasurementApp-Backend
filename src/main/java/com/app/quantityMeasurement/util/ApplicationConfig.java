package com.app.quantityMeasurement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {

    private static final Logger logger =
            LoggerFactory.getLogger(ApplicationConfig.class);

    private static final String DEFAULT_PROPERTIES_FILE =
            "application.properties";

    private static ApplicationConfig instance;

    private final Properties properties;

    private ApplicationConfig() {
        properties = new Properties();
        loadProperties(DEFAULT_PROPERTIES_FILE);
    }

    private ApplicationConfig(String propertiesFile) {
        properties = new Properties();
        loadProperties(propertiesFile);
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public static ApplicationConfig getInstance(String propertiesFile) {
        if (instance == null) {
            instance = new ApplicationConfig(propertiesFile);
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    private void loadProperties(String propertiesFile) {
        try (InputStream inputStream =
                getClass().getClassLoader()
                        .getResourceAsStream(propertiesFile)) {
            if (inputStream != null) {
                properties.load(inputStream);
                logger.info("Loaded properties from: {}", propertiesFile);
            } else {
                logger.warn(
                        "Properties file not found: {}. " +
                        "Using defaults.", propertiesFile);
                loadDefaults();
            }
        } catch (IOException e) {
            logger.error(
                    "Failed to load properties file: {}. " +
                    "Using defaults. Error: {}",
                    propertiesFile, e.getMessage());
            loadDefaults();
        }
    }

    private void loadDefaults() {
        properties.setProperty("app.name",            "QuantityMeasurementApp");
        properties.setProperty("app.version",         "0.0.1-SNAPSHOT");
        properties.setProperty("app.repository.type", "cache");
        properties.setProperty("app.environment",     "development");

        properties.setProperty("db.driver",   "org.h2.Driver");
        properties.setProperty("db.url",
                "jdbc:h2:mem:quantity_measurement_db;DB_CLOSE_DELAY=-1");
        properties.setProperty("db.username", "sa");
        properties.setProperty("db.password", "");

        properties.setProperty("db.pool.size",               "10");
        properties.setProperty("db.pool.min.idle",           "2");
        properties.setProperty("db.pool.max.idle",           "5");
        properties.setProperty("db.pool.connection.timeout", "30000");
        properties.setProperty("db.pool.idle.timeout",       "600000");
        properties.setProperty("db.pool.max.lifetime",       "1800000");

        properties.setProperty("db.schema.auto", "true");
        properties.setProperty("db.schema.file", "db/schema.sql");

        logger.info("Loaded default properties.");
    }

    public String getProperty(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isEmpty()) {
            logger.debug(
                    "System property override found for key: {}", key);
            return systemValue;
        }
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value != null) ? value : defaultValue;
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                logger.warn(
                        "Invalid integer value for key: {}. " +
                        "Using default: {}", key, defaultValue);
            }
        }
        return defaultValue;
    }

    public long getLongProperty(String key, long defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Long.parseLong(value.trim());
            } catch (NumberFormatException e) {
                logger.warn(
                        "Invalid long value for key: {}. " +
                        "Using default: {}", key, defaultValue);
            }
        }
        return defaultValue;
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            return Boolean.parseBoolean(value.trim());
        }
        return defaultValue;
    }

    public String getDbDriver() {
        return getProperty("db.driver", "org.h2.Driver");
    }

    public String getDbUrl() {
        String env = getEnvironment();
        switch (env.toLowerCase()) {
            case "test":
                return getProperty("test.db.url",
                        getProperty("db.url"));
            case "production":
                return getProperty("prod.db.url",
                        getProperty("db.url"));
            default:
                return getProperty("dev.db.url",
                        getProperty("db.url"));
        }
    }

    public String getDbUsername() {
        String env = getEnvironment();
        switch (env.toLowerCase()) {
            case "test":
                return getProperty("test.db.username",
                        getProperty("db.username", "sa"));
            case "production":
                return getProperty("prod.db.username",
                        getProperty("db.username", "sa"));
            default:
                return getProperty("dev.db.username",
                        getProperty("db.username", "sa"));
        }
    }

    public String getDbPassword() {
        String env = getEnvironment();
        switch (env.toLowerCase()) {
            case "test":
                return getProperty("test.db.password",
                        getProperty("db.password", ""));
            case "production":
                return getProperty("prod.db.password",
                        getProperty("db.password", ""));
            default:
                return getProperty("dev.db.password",
                        getProperty("db.password", ""));
        }
    }

    public int getPoolSize() {
        return getIntProperty("db.pool.size", 10);
    }

    public int getPoolMinIdle() {
        return getIntProperty("db.pool.min.idle", 2);
    }

    public int getPoolMaxIdle() {
        return getIntProperty("db.pool.max.idle", 5);
    }

    public long getConnectionTimeout() {
        return getLongProperty("db.pool.connection.timeout", 30000L);
    }

    public long getIdleTimeout() {
        return getLongProperty("db.pool.idle.timeout", 600000L);
    }

    public long getMaxLifetime() {
        return getLongProperty("db.pool.max.lifetime", 1800000L);
    }

    public String getRepositoryType() {
        return getProperty("app.repository.type", "cache");
    }

    public String getEnvironment() {
        return getProperty("app.environment", "development");
    }

    public boolean isSchemaAutoCreate() {
        return getBooleanProperty("db.schema.auto", true);
    }

    public String getSchemaFile() {
        return getProperty("db.schema.file", "db/schema.sql");
    }

    public String getAppName() {
        return getProperty("app.name", "QuantityMeasurementApp");
    }

    public String getAppVersion() {
        return getProperty("app.version", "0.0.1-SNAPSHOT");
    }

    public void logConfiguration() {
        logger.info("=== Application Configuration ===");
        logger.info("App Name       : {}", getAppName());
        logger.info("App Version    : {}", getAppVersion());
        logger.info("Environment    : {}", getEnvironment());
        logger.info("Repository Type: {}", getRepositoryType());
        logger.info("DB Driver      : {}", getDbDriver());
        logger.info("DB URL         : {}", getDbUrl());
        logger.info("DB Username    : {}", getDbUsername());
        logger.info("Pool Size      : {}", getPoolSize());
        logger.info("Schema Auto    : {}", isSchemaAutoCreate());
        logger.info("=================================");
    }

    public static void main(String[] args) {
        ApplicationConfig config = ApplicationConfig.getInstance();
        config.logConfiguration();
    }
}