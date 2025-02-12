package com.app.quantityMeasurement.exception;

public class DatabaseException extends RuntimeException {

    private final String errorCode;
    private final String sqlState;

    public DatabaseException(String message) {
        super(message);
        this.errorCode = "DB_ERROR";
        this.sqlState  = null;
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "DB_ERROR";
        this.sqlState  = null;
    }

    public DatabaseException(String message,
                              String errorCode,
                              Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.sqlState  = null;
    }

    public DatabaseException(String message,
                              String errorCode,
                              String sqlState,
                              Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.sqlState  = sqlState;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getSqlState() {
        return sqlState;
    }

    public boolean isConnectionError() {
        return errorCode != null &&
                (errorCode.equals("DB_CONNECTION_ERROR") ||
                 errorCode.equals("DB_POOL_EXHAUSTED")   ||
                 errorCode.equals("DB_TIMEOUT"));
    }

    public boolean isQueryError() {
        return errorCode != null &&
                (errorCode.equals("DB_QUERY_ERROR") ||
                 errorCode.equals("DB_INSERT_ERROR") ||
                 errorCode.equals("DB_UPDATE_ERROR") ||
                 errorCode.equals("DB_DELETE_ERROR"));
    }

    public boolean isSchemaError() {
        return errorCode != null &&
                errorCode.equals("DB_SCHEMA_ERROR");
    }

    @Override
    public String toString() {
        return String.format(
                "DatabaseException{message='%s', " +
                "errorCode='%s', sqlState='%s'}",
                getMessage(), errorCode, sqlState);
    }

    public static DatabaseException connectionError(
            String message, Throwable cause) {
        return new DatabaseException(
                message, "DB_CONNECTION_ERROR", cause);
    }

    public static DatabaseException poolExhausted(String message) {
        return new DatabaseException(
                message, "DB_POOL_EXHAUSTED", null);
    }

    public static DatabaseException queryError(
            String message, Throwable cause) {
        return new DatabaseException(
                message, "DB_QUERY_ERROR", cause);
    }

    public static DatabaseException insertError(
            String message, Throwable cause) {
        return new DatabaseException(
                message, "DB_INSERT_ERROR", cause);
    }

    public static DatabaseException schemaError(
            String message, Throwable cause) {
        return new DatabaseException(
                message, "DB_SCHEMA_ERROR", cause);
    }

    public static DatabaseException timeoutError(String message) {
        return new DatabaseException(
                message, "DB_TIMEOUT", null);
    }

    public static void main(String[] args) {
        System.out.println("DatabaseException");

        DatabaseException connEx =
                DatabaseException.connectionError(
                        "Cannot connect to database", null);
        System.out.println(connEx);
        System.out.println("Is connection error: " +
                connEx.isConnectionError());

        DatabaseException queryEx =
                DatabaseException.queryError(
                        "Query failed", null);
        System.out.println(queryEx);
        System.out.println("Is query error: " +
                queryEx.isQueryError());

        DatabaseException poolEx =
                DatabaseException.poolExhausted(
                        "All connections in use");
        System.out.println(poolEx);
        System.out.println("Is connection error: " +
                poolEx.isConnectionError());
    }
}
