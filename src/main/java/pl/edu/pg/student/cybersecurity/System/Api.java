package pl.edu.pg.student.cybersecurity.System;


import com.mysql.cj.conf.ConnectionUrlParser;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.Map;

public class Api {

    private String DB_HOST = "localhost";
    private String DB_NAME = "cryptosecurity";
    private String DB_USERNAME = "root";
    private String DB_PASSWORD = "toor";

    private Connection connection = null;

    public Api() {
        connect();
    }

    private void connect() {
        if(connection != null) return;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + "/" + DB_NAME + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public Map<String, String> findAll() {
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                System.out.printf("%2d. %s\n", id, name);
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public boolean insert() {
        String query = "INSERT INTO users VALUES (" +
                "NULL," +
                "'test'," +
                "'test')";
        try (Statement statement = connection.createStatement()) {
            boolean result = statement.execute(query);
            if(!result) {
                int count = statement.getUpdateCount();
                System.out.println(count);
            }
            return true;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }
}