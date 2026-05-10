
CREATE TABLE IF NOT EXISTS users (
                            id SERIAL PRIMARY KEY,
                            username VARCHAR(50) UNIQUE NOT NULL,
                            password_hash VARCHAR(64) NOT NULL,
                            salt VARCHAR(20) NOT NULL
);


CREATE TABLE IF NOT EXISTS worker (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL CHECK (length(trim(name)) > 0),

                            coordinate_x REAL NOT NULL,
                            coordinate_y DOUBLE PRECISION NOT NULL,

                            creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
                            salary BIGINT NOT NULL CHECK (salary > 0),


                            position VARCHAR(50) NOT NULL,
                            status VARCHAR(50) NOT NULL,





                            org_full_name VARCHAR(694) NOT NULL,
                            org_annual_turnover REAL NOT NULL CHECK (org_annual_turnover > 0),
                            org_employees_count INTEGER NOT NULL CHECK (org_employees_count > 0),


                            owner_id INTEGER NOT NULL,
                            CONSTRAINT fk_worker_user FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);