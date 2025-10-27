CREATE TABLE IF NOT EXISTS users
(
    id                SERIAL PRIMARY KEY,
    f_name            VARCHAR(100) NOT NULL,
    s_name            VARCHAR(100) NOT NULL,
    t_name            VARCHAR(100),
    age               INT          NOT NULL,
    registration_date TIMESTAMP DEFAULT NOW()
);
