package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import db.DatabaseSession;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Student Management - Login");
        setSize(400, 300);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.anchor = GridBagConstraints.LINE_START;

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(usernameLabel, constraints);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        add(passwordLabel, constraints);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(loginButton, constraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String username = usernameField.getText().trim();
                char[] password = passwordField.getPassword();

                if (username.isEmpty() || password.length == 0) {
                    Arrays.fill(password, '\0');
                    JOptionPane.showMessageDialog(LoginFrame.this, "Please enter both username and password.");
                    return;
                }

                DatabaseSession databaseSession = new DatabaseSession(username, password);
                Arrays.fill(password, '\0');
                passwordField.setText("");

                String role;
                try (Connection connection = databaseSession.openConnection()) {
                    role = getApplicationRole(connection);
                } catch (SQLException e) {
                    databaseSession.clear();
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "Database login failed. Check your account, password, and database setup.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                    return;
                }

                if (role == null) {
                    databaseSession.clear();
                    JOptionPane.showMessageDialog(
                            LoginFrame.this,
                            "This database account has no active student-records role.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                    return;
                }

                JOptionPane.showMessageDialog(LoginFrame.this, "Login Successful! Welcome " + role);
                LoginFrame.this.dispose();
                new DashboardFrame(role, databaseSession);
            }
        });

        setVisible(true);
    }

    private String getApplicationRole(Connection connection) throws SQLException {
        String sql = "SELECT CURRENT_ROLE()";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            if (!resultSet.next()) {
                return null;
            }

            String activeRoles = resultSet.getString(1);
            if (activeRoles == null) {
                return null;
            }

            String normalizedRoles = activeRoles.toLowerCase(Locale.ROOT);
            if (normalizedRoles.contains("student_admin_role")) {
                return "Admin";
            }
            if (normalizedRoles.contains("student_staff_role")) {
                return "Staff";
            }
            if (normalizedRoles.contains("student_viewer_role")) {
                return "Viewer";
            }
            return null;
        }
    }
}
