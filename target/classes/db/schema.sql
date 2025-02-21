-- Updated schema with userId field for tracking user history

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS quantity_measurement (
                                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                    operation VARCHAR(50) NOT NULL,
    operand1 VARCHAR(255),
    operand2 VARCHAR(255),
    result VARCHAR(255),
    user_id VARCHAR(255),  -- NEW: Stores user email/username for filtering
    timestamp BIGINT DEFAULT NULL,  -- NEW: Timestamp for sorting history
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(email) ON DELETE CASCADE
    );

-- Optional: Create an index on user_id and timestamp for faster queries
CREATE INDEX IF NOT EXISTS idx_user_id ON quantity_measurement(user_id);
CREATE INDEX IF NOT EXISTS idx_user_timestamp ON quantity_measurement(user_id, timestamp);
CREATE INDEX IF NOT EXISTS idx_user_operation ON quantity_measurement(user_id, operation);
