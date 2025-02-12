package com.app.quantityMeasurement.repository;

import com.app.quantityMeasurement.entity.QuantityMeasurementEntity;
import com.app.quantityMeasurement.exception.DatabaseException;
import com.app.quantityMeasurement.util.ApplicationConfig;
import com.app.quantityMeasurement.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    QuantityMeasurementDatabaseRepository.class);

    private static final String INSERT_SQL =
            "INSERT INTO quantity_measurement_entity " +
            "(this_value, this_unit, this_measurement_type, " +
            " that_value, that_unit, that_measurement_type, " +
            " operation, result_value, result_unit, " +
            " result_measurement_type, result_string, " +
            " is_error, error_message, created_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
            "SELECT * FROM quantity_measurement_entity " +
            "ORDER BY created_at DESC";

    private static final String SELECT_BY_OPERATION_SQL =
            "SELECT * FROM quantity_measurement_entity " +
            "WHERE operation = ? " +
            "ORDER BY created_at DESC";

    private static final String SELECT_BY_TYPE_SQL =
            "SELECT * FROM quantity_measurement_entity " +
            "WHERE this_measurement_type = ? " +
            "OR that_measurement_type = ? " +
            "ORDER BY created_at DESC";

    private static final String COUNT_SQL =
            "SELECT COUNT(*) FROM " +
            "quantity_measurement_entity";

    private static final String DELETE_ALL_SQL =
            "DELETE FROM quantity_measurement_entity";

    private static final String DELETE_HISTORY_SQL =
            "DELETE FROM quantity_measurement_history";

    private final ConnectionPool    connectionPool;
    private final ApplicationConfig config;

    public QuantityMeasurementDatabaseRepository() {
        this.config         = ApplicationConfig.getInstance();
        this.connectionPool = ConnectionPool.getInstance();
        if (config.isSchemaAutoCreate()) {
            initializeSchema();
        }
        logger.info(
                "QuantityMeasurementDatabaseRepository initialized.");
    }

    public QuantityMeasurementDatabaseRepository(
            ConnectionPool connectionPool,
            ApplicationConfig config) {
        this.connectionPool = connectionPool;
        this.config         = config;
        if (config.isSchemaAutoCreate()) {
            initializeSchema();
        }
        logger.info(
                "QuantityMeasurementDatabaseRepository initialized " +
                "with injected ConnectionPool.");
    }

    private void initializeSchema() {
        String schemaFile = config.getSchemaFile();
        logger.info("Initializing database schema " +
                "from: {}", schemaFile);
        try (InputStream is =
                     getClass().getClassLoader()
                             .getResourceAsStream(schemaFile)) {
            if (is == null) {
                logger.warn("Schema file not found: {}. " +
                        "Skipping schema creation.", schemaFile);
                return;
            }
            String sql = new BufferedReader(
                    new InputStreamReader(is))
                    .lines()
                    .collect(Collectors.joining("\n"));

            executeSchemaStatements(sql);
            logger.info("Database schema initialized " +
                    "successfully.");
        } catch (IOException e) {
            throw DatabaseException.schemaError(
                    "Failed to read schema file: " +
                    schemaFile, e);
        }
    }

    private void executeSchemaStatements(String sql) {
        Connection connection = connectionPool.getConnection();
        try {
            String[] statements = sql.split(";");
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    try (Statement stmt =
                                 connection.createStatement()) {
                        stmt.execute(trimmed);
                        logger.debug("Executed schema " +
                                "statement: {}",
                                trimmed.substring(0,
                                        Math.min(50,
                                        trimmed.length())));
                    }
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.schemaError(
                    "Failed to execute schema statement.", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        if (entity == null) {
            logger.warn("Attempted to save null entity.");
            return;
        }
        Connection connection =
                connectionPool.getConnection();
        try (PreparedStatement ps =
                     connection.prepareStatement(
                             INSERT_SQL,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1,  entity.thisValue);
            ps.setString(2,  entity.thisUnit);
            ps.setString(3,  entity.thisMeasurementType);
            ps.setDouble(4,  entity.thatValue);
            ps.setString(5,  entity.thatUnit);
            ps.setString(6,  entity.thatMeasurementType);
            ps.setString(7,  entity.operation);
            ps.setDouble(8,  entity.resultValue);
            ps.setString(9,  entity.resultUnit);
            ps.setString(10, entity.resultMeasurementType);
            ps.setString(11, entity.resultString);
            ps.setBoolean(12, entity.isError);
            ps.setString(13, entity.errorMessage);
            ps.setTimestamp(14, entity.createdAt != null
                    ? Timestamp.valueOf(entity.createdAt)
                    : Timestamp.valueOf(LocalDateTime.now()));

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys =
                             ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        logger.debug(
                                "Entity saved to database. " +
                                "ID: {}, Operation: {}",
                                generatedKeys.getLong(1),
                                entity.operation);
                    }
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.insertError(
                    "Failed to save entity to database.", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<QuantityMeasurementEntity>
            getAllMeasurements() {
        List<QuantityMeasurementEntity> results =
                new ArrayList<>();
        Connection connection =
                connectionPool.getConnection();
        try (PreparedStatement ps =
                     connection.prepareStatement(
                             SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                results.add(mapResultSetToEntity(rs));
            }
            logger.debug("Retrieved {} measurements " +
                    "from database.", results.size());
        } catch (SQLException e) {
            throw DatabaseException.queryError(
                    "Failed to retrieve all measurements.", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return results;
    }

    @Override
    public List<QuantityMeasurementEntity>
            getMeasurementsByOperation(String operation) {
        if (operation == null) return new ArrayList<>();
        List<QuantityMeasurementEntity> results =
                new ArrayList<>();
        Connection connection =
                connectionPool.getConnection();
        try (PreparedStatement ps =
                     connection.prepareStatement(
                             SELECT_BY_OPERATION_SQL)) {
            ps.setString(1, operation.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToEntity(rs));
                }
            }
            logger.debug(
                    "getMeasurementsByOperation({}). " +
                    "Found: {}",
                    operation, results.size());
        } catch (SQLException e) {
            throw DatabaseException.queryError(
                    "Failed to query by operation: " +
                    operation, e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return results;
    }

    @Override
    public List<QuantityMeasurementEntity>
            getMeasurementsByType(String measurementType) {
        if (measurementType == null) return new ArrayList<>();
        List<QuantityMeasurementEntity> results =
                new ArrayList<>();
        Connection connection =
                connectionPool.getConnection();
        try (PreparedStatement ps =
                     connection.prepareStatement(
                             SELECT_BY_TYPE_SQL)) {
            ps.setString(1, measurementType);
            ps.setString(2, measurementType);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResultSetToEntity(rs));
                }
            }
            logger.debug(
                    "getMeasurementsByType({}). Found: {}",
                    measurementType, results.size());
        } catch (SQLException e) {
            throw DatabaseException.queryError(
                    "Failed to query by type: " +
                    measurementType, e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return results;
    }

    @Override
    public int getTotalCount() {
        Connection connection =
                connectionPool.getConnection();
        try (PreparedStatement ps =
                     connection.prepareStatement(COUNT_SQL);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                logger.debug("Total count: {}", count);
                return count;
            }
        } catch (SQLException e) {
            throw DatabaseException.queryError(
                    "Failed to get total count.", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        Connection connection =
                connectionPool.getConnection();
        try {
            connection.setAutoCommit(false);

            try (Statement stmt =
                         connection.createStatement()) {
                stmt.execute(DELETE_HISTORY_SQL);
                stmt.execute(DELETE_ALL_SQL);
            }

            connection.commit();
            connection.setAutoCommit(true);
            logger.info("All measurements deleted " +
                    "from database.");
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
                logger.error("Transaction rolled back " +
                        "during deleteAll.");
            } catch (SQLException rollbackEx) {
                logger.error("Rollback failed: {}",
                        rollbackEx.getMessage());
            }
            throw DatabaseException.queryError(
                    "Failed to delete all measurements.", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public String getPoolStatistics() {
        return connectionPool.getPoolStatistics();
    }

    @Override
    public void releaseResources() {
        logger.info("Releasing DatabaseRepository resources.");
        ConnectionPool.resetInstance();
    }

    private QuantityMeasurementEntity mapResultSetToEntity(
            ResultSet rs) throws SQLException {
        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.thisValue           =
                rs.getDouble("this_value");
        entity.thisUnit            =
                rs.getString("this_unit");
        entity.thisMeasurementType =
                rs.getString("this_measurement_type");
        entity.thatValue           =
                rs.getDouble("that_value");
        entity.thatUnit            =
                rs.getString("that_unit");
        entity.thatMeasurementType =
                rs.getString("that_measurement_type");
        entity.operation           =
                rs.getString("operation");
        entity.resultValue         =
                rs.getDouble("result_value");
        entity.resultUnit          =
                rs.getString("result_unit");
        entity.resultMeasurementType =
                rs.getString("result_measurement_type");
        entity.resultString        =
                rs.getString("result_string");
        entity.isError             =
                rs.getBoolean("is_error");
        entity.errorMessage        =
                rs.getString("error_message");

        Timestamp createdAt =
                rs.getTimestamp("created_at");
        if (createdAt != null) {
            entity.setCreatedAt(
                    createdAt.toLocalDateTime());
        }
        return entity;
    }

    public static void main(String[] args) {
        System.out.println(
                "QuantityMeasurementDatabaseRepository");
    }
}