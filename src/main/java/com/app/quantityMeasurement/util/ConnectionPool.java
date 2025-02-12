package com.app.quantityMeasurement.util;

import com.app.quantityMeasurement.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {

    private static final Logger logger =
            LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;

    private final ApplicationConfig config;

    private final BlockingQueue<Connection> availableConnections;
    private final List<Connection>          allConnections;

    private final int    poolSize;
    private final long   connectionTimeout;
    private final String dbDriver;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    private final AtomicInteger activeConnections =
            new AtomicInteger(0);
    private final AtomicInteger totalCreated      =
            new AtomicInteger(0);

    private volatile boolean isShutdown = false;

    private ConnectionPool(ApplicationConfig config) {
        this.config            = config;
        this.poolSize          = config.getPoolSize();
        this.connectionTimeout = config.getConnectionTimeout();
        this.dbDriver          = config.getDbDriver();
        this.dbUrl             = config.getDbUrl();
        this.dbUsername        = config.getDbUsername();
        this.dbPassword        = config.getDbPassword();

        this.availableConnections =
                new ArrayBlockingQueue<>(poolSize);
        this.allConnections       =
                new ArrayList<>(poolSize);

        logger.info("Initializing ConnectionPool with size: {}",
                poolSize);
        initializePool();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(
                            ApplicationConfig.getInstance());
                }
            }
        }
        return instance;
    }

    public static ConnectionPool getInstance(ApplicationConfig config) {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(config);
                }
            }
        }
        return instance;
    }

    public static void resetInstance() {
        if (instance != null) {
            instance.shutdown();
            instance = null;
        }
    }

    private void initializePool() {
        try {
            Class.forName(dbDriver);
            logger.info("Loaded JDBC driver: {}", dbDriver);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException(
                    "JDBC driver not found: " + dbDriver, e);
        }

        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = createConnection();
                availableConnections.offer(connection);
                allConnections.add(connection);
                totalCreated.incrementAndGet();
                logger.debug("Created connection {}/{}", i + 1, poolSize);
            } catch (SQLException e) {
                logger.error(
                        "Failed to create connection {}/{}: {}",
                        i + 1, poolSize, e.getMessage());
                throw new DatabaseException(
                        "Failed to initialize connection pool", e);
            }
        }

        logger.info("ConnectionPool initialized with {} connections.",
                poolSize);
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                dbUrl, dbUsername, dbPassword);
    }

    public Connection getConnection() {
        if (isShutdown) {
            throw new DatabaseException(
                    "ConnectionPool has been shut down.");
        }
        try {
            Connection connection = availableConnections.poll(
                    connectionTimeout, TimeUnit.MILLISECONDS);
            if (connection == null) {
                throw new DatabaseException(
                        "Timeout: No connection available in pool. " +
                        "Active: " + activeConnections.get() +
                        " / Total: " + poolSize);
            }
            if (!isConnectionValid(connection)) {
                logger.warn("Invalid connection detected. " +
                        "Creating replacement.");
                connection = createConnection();
            }
            activeConnections.incrementAndGet();
            logger.debug("Connection acquired. Active: {}/{}",
                    activeConnections.get(), poolSize);
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DatabaseException(
                    "Interrupted while waiting for connection.", e);
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to create replacement connection.", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) return;
        if (isShutdown) {
            closeConnectionQuietly(connection);
            return;
        }
        try {
            if (!connection.isClosed()) {
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                availableConnections.offer(connection);
                activeConnections.decrementAndGet();
                logger.debug("Connection released. Active: {}/{}",
                        activeConnections.get(), poolSize);
            } else {
                logger.warn("Attempted to release closed connection. " +
                        "Creating replacement.");
                try {
                    Connection replacement = createConnection();
                    availableConnections.offer(replacement);
                    activeConnections.decrementAndGet();
                } catch (SQLException e) {
                    logger.error(
                            "Failed to create replacement connection: {}",
                            e.getMessage());
                }
            }
        } catch (SQLException e) {
            logger.error("Error releasing connection: {}",
                    e.getMessage());
            closeConnectionQuietly(connection);
            activeConnections.decrementAndGet();
        }
    }

    private boolean isConnectionValid(Connection connection) {
        try {
            return connection != null
                    && !connection.isClosed()
                    && connection.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    private void closeConnectionQuietly(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error("Error closing connection: {}",
                        e.getMessage());
            }
        }
    }

    public void shutdown() {
        isShutdown = true;
        logger.info("Shutting down ConnectionPool...");
        for (Connection connection : allConnections) {
            closeConnectionQuietly(connection);
        }
        availableConnections.clear();
        allConnections.clear();
        activeConnections.set(0);
        logger.info("ConnectionPool shutdown complete.");
    }

    public int getAvailableConnectionCount() {
        return availableConnections.size();
    }

    public int getActiveConnectionCount() {
        return activeConnections.get();
    }

    public int getTotalPoolSize() {
        return poolSize;
    }

    public int getTotalCreated() {
        return totalCreated.get();
    }

    public boolean isShutdown() {
        return isShutdown;
    }

    public String getPoolStatistics() {
        return String.format(
                "ConnectionPool Statistics: " +
                "[Total=%d, Active=%d, Available=%d, Shutdown=%b]",
                poolSize,
                activeConnections.get(),
                availableConnections.size(),
                isShutdown);
    }

    public static void main(String[] args) {
        System.out.println("ConnectionPool");
    }
}