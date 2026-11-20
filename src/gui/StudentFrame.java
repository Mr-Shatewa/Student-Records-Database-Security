package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import db.DatabaseSession;
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
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import java.util.concurrent.ExecutionException;

public class StudentFrame extends JFrame {
    private final StudentManager studentManager;
    private DefaultTableModel model;
    private int editingStudentId = -1;

    public StudentFrame(String userRole, boolean readOnly, DatabaseSession databaseSession) {
        studentManager = new StudentManager(databaseSession, userRole);
        boolean isAdmin = "Admin".equalsIgnoreCase(userRole);
        boolean canRegisterStudents = isAdmin || "Staff".equalsIgnoreCase(userRole);
        boolean canManageStudents = canRegisterStudents && !readOnly;
        boolean canEditStudents = isAdmin && !readOnly;

        setTitle(canManageStudents ? "Manage Students" : "View Student Records");
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
        if (canManageStudents) {
            add(formPanel, BorderLayout.NORTH);
        }

        String[] columnNames = {"ID", "First Name", "Last Name", "Gender", "Programme", "Email"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable studentTable = new JTable(model);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel actionsPanel = new JPanel();
        JButton editSelectedButton = new JButton("Edit Selected");
        JButton deleteSelectedButton = new JButton("Delete Selected");
        JButton clearFormButton = new JButton("Clear Form");
        JButton backToDashboardButton = new JButton("Back to Dashboard");
        if (canEditStudents) {
            actionsPanel.add(editSelectedButton);
            actionsPanel.add(deleteSelectedButton);
        }
        if (canManageStudents) {
            actionsPanel.add(clearFormButton);
        }
        actionsPanel.add(backToDashboardButton);
        add(actionsPanel, BorderLayout.SOUTH);

        if (canEditStudents) {
            editSelectedButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    int selectedRow = studentTable.getSelectedRow();
                    if (selectedRow == -1) {
                        JOptionPane.showMessageDialog(
                                StudentFrame.this,
                                "Please select a student to edit.");
                        return;
                    }

                    int modelRow = studentTable.convertRowIndexToModel(selectedRow);
                    editingStudentId = (int) model.getValueAt(modelRow, 0);
                    firstNameField.setText((String) model.getValueAt(modelRow, 1));
                    lastNameField.setText((String) model.getValueAt(modelRow, 2));
                    genderField.setText((String) model.getValueAt(modelRow, 3));
                    programmeField.setText((String) model.getValueAt(modelRow, 4));
                    emailField.setText((String) model.getValueAt(modelRow, 5));
                    addStudentButton.setText("Save Changes");
                }
            });

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

                    int modelRow = studentTable.convertRowIndexToModel(selectedRow);
                    int studentId = (int) model.getValueAt(modelRow, 0);
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
        }

        if (canManageStudents) {
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

                    Student student = new Student(
                            editingStudentId == -1 ? 0 : editingStudentId,
                            firstName,
                            lastName,
                            gender,
                            programme,
                            email);
                    boolean success = editingStudentId == -1
                            ? studentManager.addStudent(student)
                            : studentManager.updateStudent(student);
                    if (success) {
                        JOptionPane.showMessageDialog(
                                StudentFrame.this,
                                editingStudentId == -1
                                        ? "Student added successfully!"
                                        : "Student updated successfully!");
                        clearForm(
                                firstNameField,
                                lastNameField,
                                genderField,
                                programmeField,
                                emailField,
                                addStudentButton);
                        loadTableData();
                    } else {
                        JOptionPane.showMessageDialog(
                                StudentFrame.this,
                                editingStudentId == -1
                                        ? "Error adding student."
                                        : "Error updating student.");
                    }
                }
            });

            clearFormButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    clearForm(
                            firstNameField,
                            lastNameField,
                            genderField,
                            programmeField,
                            emailField,
                            addStudentButton);
                }
            });
        }

        backToDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                StudentFrame.this.dispose();
                new DashboardFrame(userRole, databaseSession);
            }
        });

        setVisible(true);
        loadTableData();
    }

    private void clearForm(
            JTextField firstNameField,
            JTextField lastNameField,
            JTextField genderField,
            JTextField programmeField,
            JTextField emailField,
            JButton addStudentButton) {
        editingStudentId = -1;
        firstNameField.setText("");
        lastNameField.setText("");
        genderField.setText("");
        programmeField.setText("");
        emailField.setText("");
        addStudentButton.setText("Add Student");
    }

    private void loadTableData() {
        new SwingWorker<List<Student>, Void>() {
            @Override
            protected List<Student> doInBackground() throws SQLException {
                return studentManager.getAllStudents();
            }

            @Override
            protected void done() {
                try {
                    List<Student> students = get();
                    model.setRowCount(0);
                    for (Student student : students) {
                        model.addRow(new Object[] {
                                student.getStudentId(),
                                student.getFirstName(),
                                student.getLastName(),
                                student.getGender(),
                                student.getProgramme(),
                                student.getEmail()
                        });
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(
                            StudentFrame.this,
                            "Loading student records was interrupted.",
                            "Load Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ExecutionException e) {
                    JOptionPane.showMessageDialog(
                            StudentFrame.this,
                            "Unable to load student records: " + e.getCause().getMessage(),
                            "Load Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}
