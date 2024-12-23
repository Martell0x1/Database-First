/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package org.example;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author martell
 */
public class RegisterSubject extends javax.swing.JPanel {
    private Person person;
    private DatabaseOperations instance;
    private ArrayList<Courses> courslist;

    /**
     * Creates new form RegisterSubject
     */
    public RegisterSubject(Person p) {
        initComponents();
        this.person = p;
        courslist = new ArrayList<>();
        try {
            instance = DatabaseOperations.getInstance();
            LoadPersonInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadPersonInfo() throws SQLException {

        ArrayList<Integer> list = instance.GetProfData(person);

        this.Name.setText(this.person.getFirstName());
        this.LastName.setText(this.person.getLastName());
        this.Age.setText(String.valueOf(this.person.getAge()));
        this.Gender.setSelectedItem(person.getGender());
        this.Address.setText(this.person.getAddress());
        this.Email.setText(this.person.getEmail());
        this.Phone.setText(this.person.getPhone());

        if (person.getRole() == "Student") {
            ArrayList<String> vec = instance.GetStudentData(person);
            this.UniIdText.setText(vec.get(0));
            this.CGAP.setText(vec.get(1));
        }


    }


    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        Name = new javax.swing.JTextField();
        LastName = new javax.swing.JTextField();
        Age = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Gender = new javax.swing.JComboBox<>();
        CGAP = new javax.swing.JTextField();
        Address = new javax.swing.JTextField();
        Email = new javax.swing.JTextField();
        UniIdText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        uniLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Phone = new javax.swing.JTextField();
        programming = new javax.swing.JCheckBox();
        os = new javax.swing.JCheckBox();
        dsa = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();

        jLabel4.setText("Last Name");

        Name.setEditable(false);
        Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameActionPerformed(evt);
            }
        });

        LastName.setEditable(false);
        LastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LastNameActionPerformed(evt);
            }
        });

        Age.setEditable(false);

        jLabel6.setText("CGPA");

        Gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Male", "Female"}));
        Gender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenderActionPerformed(evt);
            }
        });

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (programming.isSelected()) courslist.add(new Courses(1, "programming", 3));
                if (os.isSelected()) courslist.add(new Courses(2, "os", 3));
                if (dsa.isSelected()) courslist.add(new Courses(3, "dsa", 3));
                try {
                    boolean flag = instance.RegisterSubject_(person, courslist);
                    if (flag)
                        JOptionPane.showMessageDialog(null, "Register Sucessded !", "Okay !", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(null, "Register Failed", "Failed", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                courslist.clear();

            }
        });

        CGAP.setEditable(false);

        Address.setEditable(false);

        Email.setEditable(false);

        UniIdText.setEditable(false);

        jLabel1.setText("First Name");

        jLabel3.setText("Age:");

        jLabel5.setText("Gender:");

        jLabel7.setText("Address:");

        jLabel9.setText("Email");

        uniLabel.setText("UniversityID:");

        jLabel2.setText("Phone");

        Phone.setEditable(false);
        Phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhoneActionPerformed(evt);
            }
        });

        programming.setText("Programming");

        os.setText("OS");

        dsa.setText("DSA");

        jButton1.setText("Register Subject(s)");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addGap(67, 67, 67)
                                                                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(4, 4, 4)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jLabel2))
                                                                                .addGap(67, 67, 67)
                                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(Age, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(313, 313, 313)
                                                                                .addComponent(jLabel6)
                                                                                .addGap(53, 53, 53)
                                                                                .addComponent(CGAP, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(1, 1, 1)
                                                                .addComponent(jLabel4)
                                                                .addGap(69, 69, 69)
                                                                .addComponent(LastName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addComponent(programming)
                                                .addGap(144, 144, 144)
                                                .addComponent(os)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(140, 140, 140)
                                                .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(dsa)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel5)
                                                                .addComponent(jLabel7)
                                                                .addComponent(uniLabel)))
                                                .addGap(56, 56, 56)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(UniIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(28, 28, 28))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(289, 289, 289)
                                .addComponent(jButton1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel7)
                                                        .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel9)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(UniIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(LastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel3)
                                                        .addComponent(Age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(21, 21, 21)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel2)
                                                        .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(uniLabel))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(112, 112, 112)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(programming, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(os)
                                                        .addComponent(dsa)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(CGAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel6))))
                                .addGap(43, 43, 43)
                                .addComponent(jButton1)
                                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void NameActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void LastNameActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void GenderActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void PhoneActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }


    // Variables declaration - do not modify
    private javax.swing.JTextField Address;
    private javax.swing.JTextField Age;
    private javax.swing.JTextField CGAP;
    private javax.swing.JTextField Email;
    private javax.swing.JComboBox<String> Gender;
    private javax.swing.JTextField LastName;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Phone;
    private javax.swing.JTextField UniIdText;
    private javax.swing.JCheckBox dsa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JCheckBox os;
    private javax.swing.JCheckBox programming;
    private javax.swing.JLabel uniLabel;
    // End of variables declaration
}
