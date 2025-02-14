package com.quantitymeasurement.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayDeque;
import java.util.Queue;

public class ConnectionPool {

    private Queue<Connection> pool = new ArrayDeque<>();
    private int maxPoolSize;

    public ConnectionPool() {

        try {
            Class.forName(ApplicationConfig.getProperty("db.driver"));

            maxPoolSize =
                    Integer.parseInt(ApplicationConfig.getProperty("db.pool.size"));

            for (int i = 0; i < maxPoolSize; i++) {
                pool.add(createConnection());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Connection createConnection() throws Exception {

        return DriverManager.getConnection(
                ApplicationConfig.getProperty("db.url"),
                ApplicationConfig.getProperty("db.username"),
                ApplicationConfig.getProperty("db.password"));
    }

    public synchronized Connection getConnection() {

        if (pool.isEmpty())
            throw new RuntimeException("No connections available");

        return pool.poll();
    }

    public synchronized void releaseConnection(Connection connection) {

        if (pool.size() < maxPoolSize)
            pool.offer(connection);
    }
}