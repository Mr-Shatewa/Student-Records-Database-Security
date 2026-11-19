-- File: create_roles.sql
-- Purpose: Creates database security roles.

USE student_records_db;

CREATE ROLE IF NOT EXISTS 'student_admin_role'@'%';
CREATE ROLE IF NOT EXISTS 'student_staff_role'@'%';
CREATE ROLE IF NOT EXISTS 'student_viewer_role'@'%';