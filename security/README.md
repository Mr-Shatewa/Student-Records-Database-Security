# Security

This folder contains database roles, users, permissions, authentication, and
security testing scripts.

## Database setup

Run these scripts in order from MySQL Workbench or the MySQL command line:

1. `database/create_database.sql`
2. `database/create_tables.sql`
3. `security/create_roles.sql`
4. `security/create_users.sql`
5. `security/grant_permissions.sql`

Replace the sample password values in `create_users.sql` with unique strong
passwords before running it. Log into the Java application using the matching
database username and password. The application uses the active MySQL role for
the session; it does not use a shared database account.

`CREATE USER IF NOT EXISTS` does not change passwords for accounts that already
exist. For those accounts, set the same password in MySQL with
`ALTER USER 'account_name'@'localhost' IDENTIFIED BY 'your_new_password';`.

To inspect role grants and the active identity, run
`security/verify_permissions.sql`. `CURRENT_USER()` and `CURRENT_ROLE()` report
the account and roles for the current MySQL connection.
