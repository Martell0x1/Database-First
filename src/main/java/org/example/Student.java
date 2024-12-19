package org.example;

import java.util.Date;

public class Student extends Person{
    private int GPA;
    private int UniversityId;

    public Student(String firstName, String lastName, Date birthDate, String address, String email, String password, String gender,String Phone ,String role) {
        super(firstName, lastName, birthDate, address, email, password, gender, Phone,role);
    }

    public int getGPA() {
        return GPA;
    }

    public void setGPA(int GPA) {
        this.GPA = GPA;
    }

    public int getUniversityId() {
        return UniversityId;
    }

    public void setUniversityId(int universityId) {
        UniversityId = universityId;
    }
}
