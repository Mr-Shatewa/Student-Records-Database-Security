
package manager;

import db.DatabaseSession;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentManager {

    private final DatabaseSession databaseSession;
    private final String userRole;

    public StudentManager(DatabaseSession databaseSession, String userRole) {
        this.databaseSession = databaseSession;
        this.userRole = userRole;
    }

    private boolean hasPermission(String action) {
        String role = userRole == null ? "" : userRole.toUpperCase(Locale.ROOT);
        return SecurityManager.hasPermission(role, action);
    }

    // ADD STUDENT
    public boolean addStudent(Student student) {

        if (!hasPermission("ADD")) {
            System.out.println("Access denied: You cannot add students.");
            return false;
        }

        String sql = "INSERT INTO students "
                + "(first_name, last_name, gender, programme, email) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = databaseSession.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getGender());
            pstmt.setString(4, student.getProgramme());
            pstmt.setString(5, student.getEmail());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // VIEW ALL STUDENTS
    public List<Student> getAllStudents() throws SQLException {

        if (!hasPermission("VIEW")) {
            throw new SQLException("Access denied: You cannot view students.");
        }

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = databaseSession.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
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

        return students;
    }

    // DELETE STUDENT
    public boolean deleteStudent(int studentId) {

        if (!hasPermission("DELETE")) {
            System.out.println("Access denied: You cannot delete students.");
            return false;
        }

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = databaseSession.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, studentId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE STUDENT
    public boolean updateStudent(Student student) {

        if (!hasPermission("UPDATE")) {
            System.out.println("Access denied: You cannot update students.");
            return false;
        }

        String sql = "UPDATE students SET first_name = ?, last_name = ?, "
                + "gender = ?, programme = ?, email = ? "
                + "WHERE student_id = ?";

        try (Connection conn = databaseSession.openConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getGender());
            pstmt.setString(4, student.getProgramme());
            pstmt.setString(5, student.getEmail());
            pstmt.setInt(6, student.getStudentId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
