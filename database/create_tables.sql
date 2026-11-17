-- File: create_tables.sql
-- Purpose: Creates the students table and its columns.

USE student_records_db;

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    programme VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);