-- Add selected fields to the Problem table with defaults to avoid null issues

ALTER TABLE problem
ADD COLUMN function_name VARCHAR(255),
ADD COLUMN constraints TEXT,
ADD COLUMN hint TEXT,
ADD COLUMN is_premium BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN visibility VARCHAR(20) NOT NULL DEFAULT 'PUBLIC';

-- Create StarterCode table
CREATE TABLE starter_code (
    id BIGSERIAL PRIMARY KEY,
    language VARCHAR(100) NOT NULL,
    code TEXT NOT NULL,
    problem_id BIGINT NOT NULL,
    CONSTRAINT fk_starter_code_problem
        FOREIGN KEY (problem_id) REFERENCES problem(id)
        ON DELETE CASCADE
);
