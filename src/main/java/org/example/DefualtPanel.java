/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package org.example;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author martell
 */
public class DefualtPanel extends javax.swing.JPanel {
    private Person person;
    private Student student;
    private Professor professor;
    private DatabaseOperations instance;
    Map<Integer,String> mp = new HashMap<Integer, String>();
    Map<String,Integer> mpr = new HashMap<String,Integer>();
    public DefualtPanel(Person person) {
        initComponents();
        delete.setVisible(false);
        mp.put(1,"programming");
        mpr.put("programming",1);
        mp.put(2,"os");
        mpr.put("os",2);
        mp.put(3,"dsa");
        mpr.put("dsa",3);
        this.person = person;
        try {
            this.instance = DatabaseOperations.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
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



        if(this.person.getRole().equals("Student")){
            this.Salary.setVisible(false);
            this.SalaryLabel.setVisible(false);
            this.SubjectLabel.setVisible(false);
            this.SubjectSelect.setVisible(false);
            ;

            ArrayList<String> vec = instance.GetStudentData(person);
            this.UniIdText.setText(vec.get(0));
            this.CGPA.setText(vec.get(1));
        }
        else{
            this.uniLabel.setVisible(false);
            this.UniIdText.setVisible(false);
            this.Salary.setText(String.valueOf(list.get(0)));
            this.SubjectSelect.setSelectedItem(mp.get(list.get(1)));
            this.CGPA.setVisible(false);

        }
    }
    private Person SetPerson(){
        Person x = new Person();
        x.setFirstName(Name.getText());
        x.setLastName(LastName.getText());
        x.setRole(person.getRole());
        x.setBirthDate(person.getBirthDate());
        x.setAge(Integer.parseInt(Age.getText()));
        x.setId(person.getId());
        x.setAddress(Address.getText());
        x.setGender((String) Gender.getSelectedItem());
        x.setPassword(person.getPassword());
        x.setEmail(Email.getText());
        x.setPhone(Phone.getText());
        return x;
    }
    private void SetStudent(Person NewPerson){
        student = new Student(NewPerson);
        student.setUniversityId(Integer.parseInt(this.UniIdText.getText()));
    }
    private void SetProf(Person NewPerson){
        professor = new Professor(NewPerson);
        professor.setSalary(Integer.parseInt(this.Salary.getText()));
        professor.AssignCourse(
                new Courses(mpr.get(String.valueOf(this.SubjectSelect.getSelectedItem())),String.valueOf(this.SubjectSelect.getSelectedItem()),3)
        );
    }
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        uniLabel = new javax.swing.JLabel();
        SubjectLabel = new javax.swing.JLabel();
        SalaryLabel = new javax.swing.JLabel();
        delete = new javax.swing.JButton();
        Update = new javax.swing.JButton();
        Name = new javax.swing.JTextField();
        Age = new javax.swing.JTextField();
        Gender = new javax.swing.JComboBox<>();
        Address = new javax.swing.JTextField();
        Email = new javax.swing.JTextField();
        UniIdText = new javax.swing.JTextField();
        Salary = new javax.swing.JTextField();
        SubjectSelect = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        Phone = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        LastName = new javax.swing.JTextField();
        CGPA_L = new javax.swing.JLabel();
        CGPA = new javax.swing.JTextField();

        jLabel1.setText("First Name");

        jLabel3.setText("Age:");

        jLabel5.setText("Gender:");

        jLabel7.setText("Address:");

        jLabel9.setText("Email");

        uniLabel.setText("UniversityID:");

        SubjectLabel.setText("Subject:");

        SalaryLabel.setText("Salary:");

        delete.setText("Delete Account");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        Update.setText("Update Info");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    UpdateActionPerformed(evt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        Gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));


        UniIdText.setEditable(false);

        Salary.setEditable(false);

        SubjectSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "programming", "os", "dsa" }));


        jLabel2.setText("Phone");


        jLabel4.setText("Last Name");


        CGPA_L.setText("CGPA");

        CGPA.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(76, 76, 76)
                                                .addComponent(jLabel1)
                                                .addGap(67, 67, 67)
                                                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(77, 77, 77)
                                                .addComponent(jLabel4)
                                                .addGap(69, 69, 69)
                                                .addComponent(LastName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel5)
                                                                        .addComponent(jLabel7)
                                                                        .addComponent(jLabel2)
                                                                        .addComponent(CGPA_L))
                                                                .addGap(83, 83, 83)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(uniLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(jLabel9)
                                                                        .addGap(46, 46, 46)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(67, 67, 67)
                                                                                .addComponent(Age, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(140, 140, 140)
                                                                                .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(61, 61, 61)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(SalaryLabel)
                                                                                .addGap(39, 39, 39))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(SubjectLabel)
                                                                                .addGap(32, 32, 32))))
                                                        .addComponent(delete))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(60, 60, 60)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(SubjectSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(Salary, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(UniIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(CGPA, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(24, 24, 24)
                                                                .addComponent(Update, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)))))
                                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
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
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(Gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(9, 9, 9))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel2))
                                        .addComponent(Phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(uniLabel)
                                        .addComponent(UniIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(CGPA_L)
                                        .addComponent(CGPA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(SalaryLabel)
                                        .addComponent(Salary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(SubjectLabel)
                                        .addComponent(SubjectSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(delete)
                                        .addComponent(Update))
                                .addContainerGap(38, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }


    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) throws SQLException {
        System.out.println(Salary.getText());
        Person newPerson = SetPerson();
        boolean flag1 = instance.UpdatePhone(newPerson.getPhone(),newPerson.getId());
        boolean flag2;
        if(Objects.equals(newPerson.getRole(), "Student")){
            SetStudent(newPerson);
            student = new Student(newPerson);
            student.setUniversityId(Integer.parseInt(this.UniIdText.getText()));

            flag2 = instance.UpdateStudent(student);

        }
        else {
            SetProf(newPerson);
            professor = new Professor(newPerson);
            professor.setSalary(Integer.parseInt(this.Salary.getText()));
            String x = String.valueOf(this.SubjectSelect.getSelectedItem());
            Courses c = new Courses(mpr.get(x),x,3);
            professor.AssignCourse(c);
            flag2 = instance.UpdateProffessor(professor);
        }
        if(flag1 && flag2) JOptionPane.showMessageDialog(null,"Information Updated Successfully !","Okay :)",JOptionPane.INFORMATION_MESSAGE);
        else JOptionPane.showMessageDialog(null,"An Error Occured","Failed To Success",JOptionPane.ERROR_MESSAGE);

    }


    // Variables declaration - do not modify
    private javax.swing.JTextField Address;
    private javax.swing.JTextField Age;
    private javax.swing.JTextField Email;
    private javax.swing.JComboBox<String> Gender;
    private javax.swing.JTextField LastName;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Phone;
    private javax.swing.JTextField Salary;
    private javax.swing.JLabel SalaryLabel;
    private javax.swing.JLabel SubjectLabel;
    private javax.swing.JComboBox<String> SubjectSelect;
    private javax.swing.JTextField UniIdText;
    private javax.swing.JButton Update;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel CGPA_L;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField CGPA;
    private javax.swing.JLabel uniLabel;
    // End of variables declaration                   
}
