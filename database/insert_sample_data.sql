-- File: insert_sample_data.sql
-- Purpose: Inserts sample student records.

USE student_records_db;

INSERT INTO students (first_name, last_name, gender, programme, email) VALUES
('Justine', 'Mwila', 'Male', 'Software Engineering', 'justine@example.com'),
('Mary', 'Phiri', 'Female', 'Information Technology', 'mary@example.com'),
('Peter', 'Banda', 'Male', 'Cyber Security', 'peter@example.com'),
('Grace', 'Zulu', 'Female', 'Software Engineering', 'grace@example.com'),
('David', 'Tembo', 'Male', 'Information Technology', 'david@example.com');