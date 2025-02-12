-- ── Test Database Schema ──────────────────────────────────────────────────────

DROP TABLE IF EXISTS quantity_measurement_history;
DROP TABLE IF EXISTS quantity_measurement_entity;

CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id                      BIGINT          AUTO_INCREMENT PRIMARY KEY,
    this_value              DOUBLE          NOT NULL,
    this_unit               VARCHAR(50)     NOT NULL,
    this_measurement_type   VARCHAR(50)     NOT NULL,
    that_value              DOUBLE          NOT NULL,
    that_unit               VARCHAR(50)     NOT NULL,
    that_measurement_type   VARCHAR(50)     NOT NULL,
    operation               VARCHAR(50)     NOT NULL,
    result_value            DOUBLE,
    result_unit             VARCHAR(50),
    result_measurement_type VARCHAR(50),
    result_string           VARCHAR(255),
    is_error                BOOLEAN         NOT NULL DEFAULT FALSE,
    error_message           VARCHAR(500),
    created_at              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quantity_measurement_history (
    id                      BIGINT          AUTO_INCREMENT PRIMARY KEY,
    entity_id               BIGINT          NOT NULL,
    this_value              DOUBLE          NOT NULL,
    this_unit               VARCHAR(50)     NOT NULL,
    this_measurement_type   VARCHAR(50)     NOT NULL,
    that_value              DOUBLE          NOT NULL,
    that_unit               VARCHAR(50)     NOT NULL,
    that_measurement_type   VARCHAR(50)     NOT NULL,
    operation               VARCHAR(50)     NOT NULL,
    result_value            DOUBLE,
    result_unit             VARCHAR(50),
    result_measurement_type VARCHAR(50),
    result_string           VARCHAR(255),
    is_error                BOOLEAN         NOT NULL DEFAULT FALSE,
    error_message           VARCHAR(500),
    created_at              TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (entity_id)
        REFERENCES quantity_measurement_entity(id)
        ON DELETE CASCADE
);