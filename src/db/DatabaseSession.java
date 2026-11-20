package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public final class DatabaseSession {
    private final String username;
    private final char[] password;
    private boolean cleared;

    public DatabaseSession(String username, char[] password) {
        if (username == null || username.trim().isEmpty() || password == null || password.length == 0) {
            throw new IllegalArgumentException("Database username and password are required.");
        }

        this.username = username.trim();
        this.password = Arrays.copyOf(password, password.length);
    }

    public synchronized Connection openConnection() throws SQLException {
    if (cleared) {
        throw new SQLException("The database session has ended.");
    }

    return DatabaseConnection.getConnection(username, new String(password));
}

    public synchronized void clear() {
        Arrays.fill(password, '\0');
        cleared = true;
    }
}
