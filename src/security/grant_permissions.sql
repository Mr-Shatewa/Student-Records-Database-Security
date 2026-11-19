-- File: grant_permissions.sql
-- Purpose: Assigns permissions to roles and roles to users.

USE student_records_db;

-- Administrator: Full access to student records.
GRANT SELECT, INSERT, UPDATE, DELETE
ON student_records_db.students
TO 'student_admin_role'@'%';

-- Staff: Can view and register students.
GRANT SELECT, INSERT
ON student_records_db.students
TO 'student_staff_role'@'%';

-- Viewer: Can only view student records.
GRANT SELECT
ON student_records_db.students
TO 'student_viewer_role'@'%';

-- Assign roles to users.
GRANT 'student_admin_role'@'%'
TO 'admin_user'@'localhost';

GRANT 'student_staff_role'@'%'
TO 'staff_user'@'localhost';

GRANT 'student_viewer_role'@'%'
TO 'viewer_user'@'localhost';

-- Activate assigned roles by default.
SET DEFAULT ROLE ALL TO 'admin_user'@'localhost';
SET DEFAULT ROLE ALL TO 'staff_user'@'localhost';
SET DEFAULT ROLE ALL TO 'viewer_user'@'localhost';