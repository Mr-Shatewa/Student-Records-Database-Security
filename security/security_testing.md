# Database Security Testing

## 1. Introduction

This document describes the security testing performed on the
Student Records Management System database.

MySQL users, roles, GRANT and REVOKE commands are used to
control access to student information.

- Database: student_records_db
- Table: students
- Database system: MySQL

## 2. Database Users and Roles

| Database User | Assigned Role |
|---|---|
| admin_user | student_admin_role |
| staff_user | student_staff_role |
| viewer_user | student_viewer_role |

## 3. Permission Matrix

| Operation | Administrator | Staff | Viewer |
|---|---|---|---|
| SELECT | Allowed | Allowed | Allowed |
| INSERT | Allowed | Allowed | Denied |
| UPDATE | Allowed | Denied | Denied |
| DELETE | Allowed | Denied | Denied |

## 4. Security Test Results

### Test 1: Administrator Access

Tests performed:
- View student records.
- Insert student records.
- Update student records.
- Delete student records.

Expected result: All operations allowed.

Status: PASSED.

### Test 2: Staff Access

Tests performed:
- View student records.
- Insert student records.
- Update student records.
- Delete student records.

Expected result:
- SELECT and INSERT allowed.
- UPDATE and DELETE denied.

Status: PASSED.

### Test 3: Viewer Access

Tests performed:
- View student records.
- Insert student records.
- Update student records.
- Delete student records.

Expected result:
- SELECT allowed.
- INSERT, UPDATE and DELETE denied.

Status: PASSED.

### Test 4: REVOKE Permission

Action:
Removed INSERT permission from the Staff role using REVOKE.

SQL command:

REVOKE INSERT
ON student_records_db.students
FROM 'student_staff_role'@'%';

Result: The REVOKE command executed successfully.

Status: PASSED.

### Test 5: GRANT Permission Restoration

Action:
Restored INSERT permission to the Staff role using GRANT.

SQL command:

GRANT INSERT
ON student_records_db.students
TO 'student_staff_role'@'%';

Result: The GRANT command executed successfully.

Status: PASSED.

## 5. SQL Security Commands Used

| Command | Purpose |
|---|---|
| CREATE USER | Creates database user accounts. |
| CREATE ROLE | Creates database security roles. |
| GRANT | Assigns database permissions. |
| REVOKE | Removes database permissions. |
| SET DEFAULT ROLE | Activates assigned roles by default. |
| SHOW GRANTS | Displays assigned permissions. |

## 6. Conclusion

The database security implementation demonstrates how MySQL
roles and permissions can restrict access to student records.

The Administrator has full access, Staff can view and register
students, while Viewer can only view student information.

The practical tests demonstrate how GRANT and REVOKE can be
used to manage database permissions and protect student data
against unauthorised operations.