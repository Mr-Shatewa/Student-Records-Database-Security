
package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Authentication {

    private static final String URL =
        "jdbc:mysql://localhost:3306/student_records_db";

    public static Connection login(String username, String password)
            throws SQLException {

        Connection connection =
            DriverManager.getConnection(URL, username, password);

        System.out.println("Login successful!");
        return connection;
    }
}
