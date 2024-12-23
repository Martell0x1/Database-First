package org.example;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Register extends JFrame {
    private JTextField FirstName;
    private JTextField LastName;
    private JPasswordField pass1;
    private JPasswordField pass2;
    private JComboBox role;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel image;
    private JPanel Comp;
    private JPanel MainP;
    private JTextField Email;
    private JSpinner BirthDate;
    private JComboBox Gender;
    private JTextField Address;
    private JTextField uniId;

    private JLabel clabel;
    private JLabel unilabel;
    private JRadioButton ch1;
    private JRadioButton ch2;
    private JRadioButton ch3;
    private JTextField SalaryText;
    private JLabel Salary;
    private JTextField PhoneNumber;
    private Student student;
    private Professor professor;

    private void SpinnerHelper() {
        SpinnerDateModel model = new SpinnerDateModel();
        model.setStart(java.sql.Date.valueOf("1900-01-01"));
        model.setEnd(new Date());
        model.setValue(new Date());

        BirthDate.setModel(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(BirthDate, "yyyy-MM-dd");
        BirthDate.setEditor(editor);

        JFormattedTextField textField = editor.getTextField();
        textField.setEditable(false);
    }

    private void HandelRegister(ActionEvent e) {
        String FirstName = this.FirstName.getText();
        String LastName = this.LastName.getText();
        String Pass1 = new String(this.pass1.getPassword());
        String Pass2 = new String(this.pass1.getPassword());
        String Address = this.Address.getText();
        String Email = this.Email.getText();
        Date BirthDate = (Date) this.BirthDate.getValue();
        String Gender = (String) this.Gender.getSelectedItem();
        String Role = (String) this.role.getSelectedItem();
        String Salary_ = this.SalaryText.getText();
        String Phone = this.PhoneNumber.getText();

        ArrayList<String> errors = new ArrayList<>();
        if (!InputValidator.noSqli(FirstName) || !InputValidator.noSqli(LastName) || !InputValidator.noSqli(Address) || !InputValidator.noSqli(Email) || !InputValidator.noSqli(Phone)) {
            errors.add("Potential SQL-Injection Detected..");
        }
        if (!InputValidator.isNameValid(FirstName) || !InputValidator.isNameValid(LastName)) {
            errors.add("First Name or Last Name Are invalide : They must start with a capital letter and be 3-50 characters long");
        }
        if (!InputValidator.isAddressValid(Address)) {
            errors.add("Address is invalid. It must be 5-100 characters long and can include letters, numbers, spaces, and certain symbols (,. -).");
        }
        if (!InputValidator.isPhoneValid(Phone)) {
            errors.add("Phone is invalid . it must start with country-code then the number ie.+20 1234567890");
        }
        if (!InputValidator.isEmailValid(Email)) {
            errors.add("Email is invalid .It must follow the pattern: letters followed by numbers, ending with '@domain.com'.");
        }
        if (!InputValidator.isPasswordValid(Pass1)) {
            errors.add("Password must contain at least 8 characters, including a number, an uppercase letter, and a lowercase letter.");
        }
        if (!InputValidator.doesPasswordsMatch(Pass1, Pass2)) {
            errors.add("Paddwords Doesn't Match");
        }
        DatabaseOperations operations;
        try {
            operations = DatabaseOperations.getInstance();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if (!errors.isEmpty()) {
            JOptionPane.showMessageDialog(null, String.join("\n", errors), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean auth = false;
            if (Role == "Student") {
                String uni = uniId.getText();
                student = new Student(FirstName, LastName, BirthDate, Address, Email, Pass1, Gender, Phone, "Student");
                System.out.println(Pass1);
                student.setUniversityId(Integer.parseInt(uni));
                try {
                    auth = operations.Authorize(student);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                professor = new Professor(FirstName, LastName, BirthDate, Address, Email, Pass1, Gender, Phone, "Professor");
                professor.setSalary(Integer.parseInt(Salary_));
                Courses c = null;
                if (ch1.isSelected()) {
                    c = new Courses(1, ch1.getName(), 3);
                    ch2.setSelected(false);
                    ch3.setSelected(false);
                }
                if (ch2.isSelected()) {
                    c = new Courses(2, ch2.getName(), 3);
                    ch1.setSelected(false);
                    ch3.setSelected(false);
                }
                if (ch3.isSelected()) {
                    c = new Courses(3, ch3.getName(), 3);
                    ch1.setSelected(false);
                    ch2.setSelected(false);
                }
                professor.AssignCourse(c);


                try {
                    auth = operations.Authorize(professor);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if (auth) {
                JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                try {
                    if (Objects.equals(Role, "Student")) {
                        student.setAge(student.CalculateAge());
                        ArrayList<String> x = DatabaseOperations.getInstance().GetStudentData(student);
                        student.setGPA(Integer.parseInt(x.get(1)));
                        new Dashboard(student).Run();
                    } else {

                        new Dashboard(professor).Run();
                    }
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else
                JOptionPane.showMessageDialog(null, "Registration Failed Due to Database Issue!", "Failed", JOptionPane.ERROR_MESSAGE);

        }
    }


    public Register(JFrame parent) throws IOException, SQLException {

        setTitle("Login");
        setContentPane(MainP);
        setMinimumSize(new Dimension(600, 730));

        role.addItem("Student");
        role.addItem("Professor");

        Gender.addItem("Male");
        Gender.addItem("Female");
        String roleS = this.role.getSelectedItem().toString();
        if (roleS.equals("Student")) {
            clabel.setVisible(false);
            ch1.setVisible(false);
            ch2.setVisible(false);
            ch3.setVisible(false);
            Salary.setVisible(false);
            SalaryText.setVisible(false);

        } else {
            unilabel.setVisible(false);
            uniId.setVisible(false);
        }

        SpinnerHelper();


        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        registerButton.addActionListener(this::HandelRegister);

        role.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = role.getSelectedItem().toString();

                if (selectedRole.equals("Student")) {
                    clabel.setVisible(false);
                    ch1.setVisible(false);
                    ch2.setVisible(false);
                    ch3.setVisible(false);
                    Salary.setVisible(false);
                    SalaryText.setVisible(false);

                    unilabel.setVisible(true);
                    uniId.setVisible(true);
                } else {
                    unilabel.setVisible(false);
                    uniId.setVisible(false);
                    Salary.setVisible(true);
                    SalaryText.setVisible(true);
                    clabel.setVisible(true);
                    ch1.setVisible(true);
                    ch2.setVisible(true);
                    ch3.setVisible(true);
                }
            }
        });
    }


    public void Run() {
        setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainP = new JPanel();
        MainP.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        image = new JPanel();
        image.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        image.setBackground(new Color(-14910571));
        MainP.add(image, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/images/test2.png")));
        label1.setText("");
        image.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        MainP.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        Comp = new JPanel();
        Comp.setLayout(new GridLayoutManager(29, 3, new Insets(10, 10, 10, 10), -1, -1));
        Comp.setBackground(new Color(-6459824));
        MainP.add(Comp, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setIcon(new ImageIcon(getClass().getResource("/images/register.png")));
        label2.setText("");
        Comp.add(label2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("First Name");
        Comp.add(label3, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        FirstName = new JTextField();
        Comp.add(FirstName, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Last Name");
        Comp.add(label4, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LastName = new JTextField();
        LastName.setText("");
        Comp.add(LastName, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Password");
        Comp.add(label5, new GridConstraints(13, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pass1 = new JPasswordField();
        Comp.add(pass1, new GridConstraints(14, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Confirm Password");
        Comp.add(label6, new GridConstraints(15, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pass2 = new JPasswordField();
        Comp.add(pass2, new GridConstraints(16, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Role");
        Comp.add(label7, new GridConstraints(25, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        role = new JComboBox();
        Comp.add(role, new GridConstraints(26, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        registerButton = new JButton();
        registerButton.setText("Register");
        Comp.add(registerButton, new GridConstraints(27, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        Comp.add(cancelButton, new GridConstraints(28, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Email");
        Comp.add(label8, new GridConstraints(11, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Email = new JTextField();
        Comp.add(Email, new GridConstraints(12, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Birthdate");
        Comp.add(label9, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BirthDate = new JSpinner();
        Comp.add(BirthDate, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Gender");
        Comp.add(label10, new GridConstraints(23, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Gender = new JComboBox();
        Comp.add(Gender, new GridConstraints(24, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Address");
        Comp.add(label11, new GridConstraints(7, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Address = new JTextField();
        Comp.add(Address, new GridConstraints(8, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        unilabel = new JLabel();
        unilabel.setText("University ID");
        Comp.add(unilabel, new GridConstraints(17, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uniId = new JTextField();
        Comp.add(uniId, new GridConstraints(18, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        clabel = new JLabel();
        clabel.setText("Courses");
        Comp.add(clabel, new GridConstraints(21, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ch1 = new JRadioButton();
        ch1.setText("Programing");
        Comp.add(ch1, new GridConstraints(22, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ch2 = new JRadioButton();
        ch2.setText("OS");
        Comp.add(ch2, new GridConstraints(22, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ch3 = new JRadioButton();
        ch3.setText("DSA");
        Comp.add(ch3, new GridConstraints(22, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Salary = new JLabel();
        Salary.setText("Salary");
        Comp.add(Salary, new GridConstraints(19, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SalaryText = new JTextField();
        Comp.add(SalaryText, new GridConstraints(20, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Phone Number");
        Comp.add(label12, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PhoneNumber = new JTextField();
        Comp.add(PhoneNumber, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainP;
    }

}
