package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import manager.StudentManager;
import model.Student;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StudentFrame extends JFrame {
    private StudentManager studentManager = new StudentManager();
    private DefaultTableModel model;

    public StudentFrame() {
        setTitle("Student Records");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        JLabel firstNameLabel = new JLabel("First Name");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name");
        JTextField lastNameField = new JTextField();
        JLabel genderLabel = new JLabel("Gender");
        JTextField genderField = new JTextField();
        JLabel programmeLabel = new JLabel("Programme");
        JTextField programmeField = new JTextField();
        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField();
        JButton addStudentButton = new JButton("Add Student");

        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(genderLabel);
        formPanel.add(genderField);
        formPanel.add(programmeLabel);
        formPanel.add(programmeField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(addStudentButton);
        formPanel.add(new JLabel());
        add(formPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "First Name", "Last Name", "Gender", "Programme", "Email"};
        model = new DefaultTableModel(columnNames, 0);
        JTable studentTable = new JTable(model);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel actionsPanel = new JPanel();
        JButton deleteSelectedButton = new JButton("Delete Selected");
        JButton clearFormButton = new JButton("Clear Form");
        JButton backToDashboardButton = new JButton("Back to Dashboard");
        actionsPanel.add(deleteSelectedButton);
        actionsPanel.add(clearFormButton);
        actionsPanel.add(backToDashboardButton);
        add(actionsPanel, BorderLayout.SOUTH);

        deleteSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(
                            StudentFrame.this,
                            "Please select a student to delete.");
                    return;
                }

                int studentId = (int) model.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(
                        StudentFrame.this,
                        "Are you sure you want to delete this student?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                boolean success = studentManager.deleteStudent(studentId);
                if (success) {
                    JOptionPane.showMessageDialog(StudentFrame.this, "Student deleted successfully!");
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(StudentFrame.this, "Error deleting student.");
                }
            }
        });

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String gender = genderField.getText().trim();
                String programme = programmeField.getText().trim();
                String email = emailField.getText().trim();

                if (firstName.isEmpty() || lastName.isEmpty() || gender.isEmpty()
                        || programme.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(StudentFrame.this, "Please fill all fields!");
                    return;
                }

                Student newStudent = new Student(0, firstName, lastName, gender, programme, email);
                boolean success = studentManager.addStudent(newStudent);
                if (success) {
                    JOptionPane.showMessageDialog(StudentFrame.this, "Student added successfully!");
                    firstNameField.setText("");
                    lastNameField.setText("");
                    genderField.setText("");
                    programmeField.setText("");
                    emailField.setText("");
                    loadTableData();
                } else {
                    JOptionPane.showMessageDialog(StudentFrame.this, "Error adding student.");
                }
            }
        });

        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                StudentFrame.this.dispose();
            }
        });

        loadTableData();
        setVisible(true);
    }

    private void loadTableData() {
        model.setRowCount(0);
        List<Student> students = studentManager.getAllStudents();
        for (Student s : students) {
            model.addRow(new Object[] {
                    s.getStudentId(),
                    s.getFirstName(),
                    s.getLastName(),
                    s.getGender(),
                    s.getProgramme(),
                    s.getEmail()
            });
        }
    }
}
