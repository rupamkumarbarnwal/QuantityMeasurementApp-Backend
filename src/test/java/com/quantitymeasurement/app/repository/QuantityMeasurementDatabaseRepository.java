package com.quantitymeasurement.app.repository;

import com.quantitymeasurement.app.entity.QuantityMeasurementEntity;
import com.quantitymeasurement.app.util.ConnectionPool;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    private ConnectionPool connectionPool = new ConnectionPool();

    @Override
    public void initializeDatabase() {

        Connection connection = null;

        try {

            connection = connectionPool.getConnection();

            String sql = """
                    CREATE TABLE IF NOT EXISTS quantity_measurements (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    operation VARCHAR(50),
                    operand1 VARCHAR(50),
                    operand2 VARCHAR(50),
                    result VARCHAR(50)
                    )
                    """;

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        } finally {

            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {

        Connection connection = null;

        try {

            connection = connectionPool.getConnection();

            String sql = "INSERT INTO quantity_measurements (operation, operand1, operand2, result) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, entity.getOperation());
            stmt.setString(2, entity.getOperand1());
            stmt.setString(3, entity.getOperand2());
            stmt.setString(4, entity.getResult());

            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to save measurement", e);
        } finally {

            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();
        Connection connection = null;

        try {

            connection = connectionPool.getConnection();

            String sql = "SELECT * FROM quantity_measurements";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

                entity.setOperation(rs.getString("operation"));
                entity.setOperand1(rs.getString("operand1"));
                entity.setOperand2(rs.getString("operand2"));
                entity.setResult(rs.getString("result"));

                list.add(entity);
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch measurements", e);
        } finally {

            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }

        return list;
    }

    @Override
    public void deleteAll() {

        Connection connection = null;

        try {

            connection = connectionPool.getConnection();

            String sql = "DELETE FROM quantity_measurements";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete measurements", e);
        } finally {

            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
}