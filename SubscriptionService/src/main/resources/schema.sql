CREATE TABLE IF NOT EXISTS subscriptions
(
    id              SERIAL PRIMARY KEY,
    userId          BIGINT      NOT NULL,
    magazineId      BIGINT      NOT NULL,
    start_date      TIMESTAMP DEFAULT NOW() NOT NULL,
    end_date        TIMESTAMP DEFAULT NULL,
    duration_months INT,
    status          VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'CANCELLED', 'EXPIRED'))
);

