
-- File: create_users.sql
-- Purpose: Creates database user accounts.
-- Replace each placeholder with a unique strong password before running.

-- Administrator account
CREATE USER IF NOT EXISTS 'admin_user'@'localhost'
IDENTIFIED BY 'Admin123!';

-- Staff account
CREATE USER IF NOT EXISTS 'staff_user'@'localhost'
IDENTIFIED BY 'Staff123!';

-- Viewer account
CREATE USER IF NOT EXISTS 'viewer_user'@'localhost'
IDENTIFIED BY 'Viewer123!';
