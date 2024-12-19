package org.example;

import java.math.BigDecimal;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DatabaseOperations {
    private  Connection connection;
    private static DatabaseOperations instace;
    private Person person;
    private Student student;

    private DatabaseOperations() throws SQLException {
        connection = DatabaseConnect.getConnection();
        if(connection == null || connection.isClosed())
            throw new SQLException("Connection Refused");
    }
    private  boolean PersonEdit(Person person) throws SQLException {
        ResultSet rs1 = null;
        int personId = -1;

        String Query = "INSERT INTO Person(FirstName,LastName,Gender,Birthdate) VALUES (?,?,?,?)";
        PreparedStatement insert = connection.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
        insert.setString(1, person.getFirstName());
        insert.setString(2, person.getLastName());
        insert.setString(3, person.getGender());
        Date sqlDate = new Date(person.getBirthDate().getTime());
        insert.setDate(4, sqlDate);
        int rowsAffected = insert.executeUpdate();
        if (rowsAffected > 0) {
            ResultSet rs = insert.getGeneratedKeys();
            if (rs.next()) {
                personId = rs.getInt(1);
                person.setId(personId);
            } else {
                System.out.println("Error: No ID generated for Person.");
                return false;
            }
        }
        return rowsAffected > 0;
    }

    private boolean EditPhone(Person person) throws SQLException {
        String Query = "INSERT into Phone (Phone,PersonID) values (?,?)";
        PreparedStatement insert = connection.prepareStatement(Query,Statement.RETURN_GENERATED_KEYS);
        insert.setString(1,person.getPhone());
        insert.setInt(2,person.getId());
        int rowsAffected = insert.executeUpdate();
        return rowsAffected > 0;
    }

    private boolean EditStudent(Person person) throws SQLException{
        String Query2 = "INSERT INTO Student(StudentID, UniversityID) VALUES (?, ?)";
        PreparedStatement    insert = connection.prepareStatement(Query2);
        int universityId = ((Student) person).getUniversityId();
        insert.setInt(1, person.getId());
        insert.setInt(2, universityId);
        int rowsAffected = insert.executeUpdate();
        return rowsAffected > 0;
    }
    private boolean EditProessorr(Person person) throws SQLException{
        String Query3 = "INSERT INTO Professor(ProfID, Salary, CourseID) VALUES (?, ?, ?)";
        PreparedStatement insert = connection.prepareStatement(Query3);
        int courseId = ((Professor) person).getCourse().getCourseID();
        insert.setInt(1, person.getId());
        insert.setInt(2, ((Professor) person).getSalary());
        insert.setInt(3, courseId);
        int rowsAffected = insert.executeUpdate();
        return rowsAffected > 0;
    }
    private boolean LoginUpdate(Person person) throws  SQLException {
        String query  = "INSERT INTO Login (Email,Password, PersonID) values (?,?,?)";
        PreparedStatement insert = connection.prepareStatement(query);
        insert.setString(1,person.getEmail());
        insert.setString(2,person.getPassword());
        insert.setInt(3,person.getId());
        int rowsAffected = insert.executeUpdate();
        return rowsAffected > 0;

    }
    public static DatabaseOperations getInstance() throws SQLException {
        if(instace == null){
            instace = new DatabaseOperations();
        }
        return instace;
    }

    public boolean Authenticate(String Email , String Password) throws SQLException {
        String Query = "select * from Login where Email = ? and Password = ?";
        PreparedStatement login = connection.prepareStatement(Query);
        login.setString(1,Email);
        login.setString(2,Password);
        ResultSet rs = login.executeQuery();
        return rs.next();
    }



    public boolean Authorize(Person person) throws SQLException {
        return (this.PersonEdit(person) && this.EditPhone(person) && LoginUpdate(person)) && (("Student".equals(person.getRole()) && this.EditStudent(person))
                || ("Professor".equals(person.getRole()) && this.EditProessorr(person)));
    }

}
