package pl.edu.pg.student.cybersecurity.System;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Api {

    private String DB_HOST = "localhost";
    private String DB_NAME = "cybersecurity";
    private String DB_USERNAME = "root";
    private String DB_PASSWORD = "";

    private Connection connection = null;

    public Api() {
        connect();
    }

    private void connect() {
        if(connection != null) return;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + DB_HOST + "/" + DB_NAME + "?user=" + DB_USERNAME + "&password=" + DB_PASSWORD);
            System.out.println("Connected!");
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
                String login = resultSet.getString("user_login");
                String password = resultSet.getString("user_password");
                String email = resultSet.getString("user_email");
                String timestamp = resultSet.getString("created_at");
                System.out.printf("%2d. %s\t %s\t %s\t %s\n", id, login, password, email, timestamp);
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public boolean insert(String login, String password) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = simpleDateFormat.format(date);
        String query = "INSERT INTO users (user_login, user_password, created_at) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, dateString);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }

    /**
     * Updates email of a record from the database that matches the given login.
     * @param login user's login
     * @param email email to be set
     * @return TRUE if the operation was successful, otherwise FALSE
     */
    public boolean updateEmail(String login, String email) {
        String query = "UPDATE users SET user_email = ? WHERE user_login = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, login);
            statement.executeUpdate();
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