
-- Create Table customers

CREATE TABLE customers (
    id UUID PRIMARY KEY,
    --user_id UUID UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    document_type VARCHAR(50) NOT NULL CHECK (document_type IN ('DNI', 'PASSPORT', 'CE')),
    document_number VARCHAR(50) UNIQUE NOT NULL,
    birth_date DATE,
    gender VARCHAR(20) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    phone_number VARCHAR(20),
    address TEXT,
    marital_status VARCHAR(20) CHECK (marital_status IN ('SINGLE', 'MARRIED', 'DIVORCED', 'WIDOWED')),
    --department VARCHAR(100),
    --province VARCHAR(100),
    --district VARCHAR(100),
    --favorite_cinema_id UUID REFERENCES cinemas(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Get Customers
SELECT * FROM customers