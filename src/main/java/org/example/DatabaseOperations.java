package org.example;

import java.sql.*;

public class DatabaseOperations {
    private  Connection connection;

    public DatabaseOperations() throws SQLException {
        connection = DatabaseConnect.getConnection();
        if(connection == null || connection.isClosed())
            throw new SQLException("Connection Refused");
    }

    public boolean Authenticate(String Email , String Password) throws SQLException {
        String Query = "select * from creds where Email = ? and Pass = ?";
        PreparedStatement login = connection.prepareStatement(Query);
        login.setString(1,Email);
        login.setString(2,Password);
        ResultSet rs = login.executeQuery();
        return rs.next();
    }

    public ResultSet GetData(String name) throws SQLException {
        String Query = "select * from ?";
        PreparedStatement stm = connection.prepareStatement(Query);
        stm.setString(1,name);
        return stm.executeQuery();
    }
}
