CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);