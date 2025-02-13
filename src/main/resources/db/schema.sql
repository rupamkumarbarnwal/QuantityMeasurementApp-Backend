CREATE TABLE IF NOT EXISTS quantity_measurements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    operation VARCHAR(50),
    operand1 VARCHAR(100),
    operand2 VARCHAR(100),
    result VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );