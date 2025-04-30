ALTER TABLE problem ADD COLUMN function_name VARCHAR(255);
ALTER TABLE problem ADD COLUMN constraints TEXT;
ALTER TABLE problem ADD COLUMN hint TEXT;
ALTER TABLE problem ADD COLUMN is_premium BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE problem ADD COLUMN visibility VARCHAR(20) NOT NULL DEFAULT 'PUBLIC';
ALTER TABLE problem ADD COLUMN updated_at TIMESTAMP;

CREATE TABLE starter_code (
    id SERIAL PRIMARY KEY,
    language VARCHAR(50) NOT NULL,
    code TEXT NOT NULL,
    problem_id BIGINT,
    CONSTRAINT fk_problem FOREIGN KEY (problem_id)
        REFERENCES problem (id)
        ON DELETE CASCADE
);