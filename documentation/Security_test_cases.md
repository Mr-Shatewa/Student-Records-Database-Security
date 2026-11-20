
# Security Test Cases

## 1. Authentication Tests
- Valid username and password should allow login.
- Invalid password should reject login.
- Unknown username should reject login.

## 2. Role-Based Access Control Tests
- Administrator can view students.
- Administrator can add students.
- Administrator can update students.
- Administrator can delete students.
- Staff can view and add students.
- Staff cannot update or delete students.
- Viewer can only view students.

## 3. SQL Injection Tests
- Attempt SQL injection through student input.
- Verify PreparedStatement handles input safely.
- Verify malicious input is treated as data.

## 4. Database Security Tests
- Verify database users have correct permissions.
- Verify unauthorized operations are rejected.
