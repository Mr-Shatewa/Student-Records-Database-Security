-- Run this first block as a database administrator to inspect role grants.
USE student_records_db;

SHOW GRANTS FOR 'student_admin_role'@'%';
SHOW GRANTS FOR 'student_staff_role'@'%';
SHOW GRANTS FOR 'student_viewer_role'@'%';

SHOW GRANTS FOR 'admin_user'@'localhost';
SHOW GRANTS FOR 'staff_user'@'localhost';
SHOW GRANTS FOR 'viewer_user'@'localhost';

-- Connect as each application user, then run these identity and permission tests.
SELECT CURRENT_USER() AS authenticated_account, CURRENT_ROLE() AS active_roles;

-- When connected as staff_user, these should each fail with a permission error.
-- UPDATE students SET first_name = first_name WHERE student_id = -1;
-- DELETE FROM students WHERE student_id = -1;

-- When connected as viewer_user, this should fail with a permission error.
-- INSERT INTO students (first_name, last_name, gender, programme, email)
-- VALUES ('Test', 'Permission', 'Other', 'Test', 'permission-test@example.invalid');
