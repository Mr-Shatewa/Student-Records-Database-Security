-- File: revoke_permissions.sql
-- Purpose: Demonstrates removing and restoring database permissions.

USE student_records_db;

-- Remove INSERT permission from the Staff role.
REVOKE INSERT
ON student_records_db.students
FROM 'student_staff_role'@'%';

-- Display the remaining Staff permissions.
SHOW GRANTS FOR 'student_staff_role'@'%';

-- Restore INSERT permission to the Staff role.
GRANT INSERT
ON student_records_db.students
TO 'student_staff_role'@'%';

-- Display the restored Staff permissions.
SHOW GRANTS FOR 'student_staff_role'@'%';