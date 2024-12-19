package org.example;

import java.util.Date;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String address;
    private String email;
    private String password;
    private String gender;
    private String role;
    private String Phone;

    public Person(String firstName, String lastName, Date birthDate, String address,
                String email, String password, String gender,String Phone, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.password = password;
        this.gender = (gender.equals("Male") ? "M":"F");
        this.Phone = Phone;
        this.role = role;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }



    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public java.util.Date getBirthDate() { return  birthDate; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getGender() { return gender; }
    public String getRole() { return role; }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
