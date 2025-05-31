ALTER TABLE test_case
ADD COLUMN language VARCHAR(50) NOT NULL DEFAULT 'JAVA'
    CHECK (language IN ('JAVA', 'PYTHON', 'C', 'JAVASCRIPT', 'TYPESCRIPT', 'CSHARP'));

ALTER TABLE test_case
ADD COLUMN explanation TEXT;

-- I am adding this for now to handle test cases that involves linkedlist, trees, etc.
-- Until I figure out how to handle auto generating test cases in the future.
ALTER TABLE test_case
ADD COLUMN beautified_input TEXT;

-- update the language check constraints to allow JAVASCRIPT, TYPESCRIPT, and CSHARP
-- never created a check constraint for starter_code xd
ALTER TABLE starter_code
ADD CONSTRAINT starter_code_language_check
    CHECK (language IN ('JAVA', 'PYTHON', 'C', 'JAVASCRIPT', 'TYPESCRIPT', 'CSHARP'));

ALTER TABLE submission
DROP CONSTRAINT submission_language_check;

ALTER TABLE submission
ADD CONSTRAINT submission_language_check
    CHECK (language IN ('JAVA', 'PYTHON', 'C', 'JAVASCRIPT', 'TYPESCRIPT', 'CSHARP'));

ALTER TABLE wrapper_template
DROP CONSTRAINT wrapper_template_language_check;

ALTER TABLE wrapper_template
ADD CONSTRAINT wrapper_template_language_check
    CHECK (language IN ('JAVA', 'PYTHON', 'C', 'JAVASCRIPT', 'TYPESCRIPT', 'CSHARP'));
