package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import db.DatabaseSession;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DashboardFrame extends JFrame {
    private final String currentRole;
    private final DatabaseSession databaseSession;

    public DashboardFrame(String userRole, DatabaseSession databaseSession) {
        this.currentRole = userRole;
        this.databaseSession = databaseSession;
        setTitle("Dashboard - Student Records Management");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JLabel roleLabel = new JLabel("Logged in as: " + currentRole, SwingConstants.CENTER);
        add(roleLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton manageStudentsButton = new JButton("Manage Students");
        JButton viewRecordsButton = new JButton("View Records");
        JButton logoutButton = new JButton("Logout");
        buttonPanel.add(manageStudentsButton);
        buttonPanel.add(viewRecordsButton);
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.CENTER);

        boolean canManageStudents = "Admin".equalsIgnoreCase(currentRole)
                || "Staff".equalsIgnoreCase(currentRole);
        manageStudentsButton.setEnabled(canManageStudents);

        manageStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new StudentFrame(currentRole, false, databaseSession);
                DashboardFrame.this.dispose();
            }
        });

        viewRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                new StudentFrame(currentRole, true, databaseSession);
                DashboardFrame.this.dispose();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                databaseSession.clear();
                DashboardFrame.this.dispose();
                new LoginFrame();
            }
        });

        setVisible(true);
    }
}
