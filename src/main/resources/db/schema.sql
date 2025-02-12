-- ── Quantity Measurement Database Schema ──────────────────────────────────────

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS quantity_measurement_history;
DROP TABLE IF EXISTS quantity_measurement_entity;

-- ── Main Entity Table ─────────────────────────────────────────────────────────
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

-- ── History / Audit Table ─────────────────────────────────────────────────────
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

-- ── Indexes for Performance ───────────────────────────────────────────────────
CREATE INDEX IF NOT EXISTS idx_operation
    ON quantity_measurement_entity(operation);

CREATE INDEX IF NOT EXISTS idx_this_measurement_type
    ON quantity_measurement_entity(this_measurement_type);

CREATE INDEX IF NOT EXISTS idx_that_measurement_type
    ON quantity_measurement_entity(that_measurement_type);

CREATE INDEX IF NOT EXISTS idx_created_at
    ON quantity_measurement_entity(created_at);

CREATE INDEX IF NOT EXISTS idx_is_error
    ON quantity_measurement_entity(is_error);

CREATE INDEX IF NOT EXISTS idx_history_entity_id
    ON quantity_measurement_history(entity_id);

CREATE INDEX IF NOT EXISTS idx_history_operation
    ON quantity_measurement_history(operation);