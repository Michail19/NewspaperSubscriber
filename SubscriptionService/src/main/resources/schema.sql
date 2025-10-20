CREATE TABLE subscriptions
(
    id              SERIAL PRIMARY KEY,
    user_id         BIGINT      NOT NULL,
    magazine_id     BIGINT      NOT NULL,
    start_date      DATE        NOT NULL,
    end_date        DATE,
    duration_months INT         NOT NULL,
    status          VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'CANCELLED', 'EXPIRED')),
    created_at      TIMESTAMP DEFAULT NOW(),
    updated_at      TIMESTAMP DEFAULT NOW()
);

