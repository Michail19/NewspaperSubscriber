CREATE TABLE IF NOT EXISTS subscriptions
(
    id              SERIAL PRIMARY KEY,
    userId          BIGINT      NOT NULL,
    magazineId      BIGINT      NOT NULL,
    start_date      DATE        NOT NULL,
    end_date        DATE,
    duration_months INT         NOT NULL,
    status          VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'CANCELLED', 'EXPIRED')),
    created_at      TIMESTAMP DEFAULT NOW(),
    updated_at      TIMESTAMP DEFAULT NOW()
);

