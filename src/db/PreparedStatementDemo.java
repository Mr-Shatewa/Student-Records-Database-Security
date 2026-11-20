package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementDemo {

    public static void addStudent(
            Connection connection,
            String firstName,
            String lastName,
            String gender,
            String programme,
            String email) throws SQLException {

        String sql = "INSERT INTO students " +
                "(first_name, last_name, gender, programme, email) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, gender);
            statement.setString(4, programme);
            statement.setString(5, email);

            statement.executeUpdate();

            System.out.println("Student added successfully!");
        }
    }
}