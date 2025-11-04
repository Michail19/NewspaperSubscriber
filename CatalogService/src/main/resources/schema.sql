CREATE TABLE catalog (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         description TEXT,
                         price DOUBLE PRECISION NOT NULL,
                         link VARCHAR(255),
                         category_id BIGINT REFERENCES category(id),
                         series_id BIGINT REFERENCES series(id)
);

CREATE TABLE category (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE series (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(150) UNIQUE NOT NULL
);
