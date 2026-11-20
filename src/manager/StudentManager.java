package manager;

import db.DatabaseSession;
import model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private final DatabaseSession databaseSession;

    public StudentManager(DatabaseSession databaseSession) {
        this.databaseSession = databaseSession;
    }

    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (first_name, last_name, gender, programme, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseSession.openConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, student.getFirstName());
                pstmt.setString(2, student.getLastName());
                pstmt.setString(3, student.getGender());
                pstmt.setString(4, student.getProgramme());
                pstmt.setString(5, student.getEmail());

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = databaseSession.openConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                    ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getInt("student_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getString("programme"),
                            rs.getString("email"));
                    students.add(student);
                }
            }
        }

        return students;
    }

    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = databaseSession.openConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, studentId);
                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, gender = ?, programme = ?, email = ? "
                + "WHERE student_id = ?";

        try (Connection conn = databaseSession.openConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, student.getFirstName());
                pstmt.setString(2, student.getLastName());
                pstmt.setString(3, student.getGender());
                pstmt.setString(4, student.getProgramme());
                pstmt.setString(5, student.getEmail());
                pstmt.setInt(6, student.getStudentId());

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
