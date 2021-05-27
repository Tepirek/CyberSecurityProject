package pl.edu.pg.student.cybersecurity.System;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Api {

    private String DB_HOST = "localhost";
    private String DB_NAME = "cybersecurity";
    private String DB_USERNAME = "root";
    private String DB_PASSWORD = "";

    private Connection connection = null;

    public Api() {
        connect();
    }

    /**
     * Connects to the database.
     */
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

    /**
     *
     * @return
     */
    public List<User> getUsers() {
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if(!resultSet.next()) return null;
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("user_login"), resultSet.getString("user_email"));
                users.add(user);
                System.out.println(user);
            }
            return users;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    /**
     *
     * @param login user's login
     * @param password user's password
     * @return TRUE if the operation was successful, otherwise FALSE
     */
    public List<Object> insert(String login, String password) {
        if(getUser("login", login) != null) {
            return new ArrayList<>(Arrays.asList(false, "Login already exits!"));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = simpleDateFormat.format(date);
        String query = "INSERT INTO users (user_login, user_password, created_at) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, dateString);
            statement.executeUpdate();
            return new ArrayList<>(Arrays.asList(true, "Success!"));
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return new ArrayList<>(Arrays.asList(false, "Something went wrong!"));
    }

    /**
     * Updates email of a record from the database that matches the given login.
     * @param login user's login
     * @param email email to be set
     * @return TRUE if the operation was successful, otherwise FALSE
     */
    public List<Object> updateEmail(String login, String email) {
        if(getUser("login", login) == null) {
            return new ArrayList<>(Arrays.asList(false, "User with the given login doesn't exits!"));
        }
        if(getUser("email", email) != null) {
            return new ArrayList<>(Arrays.asList(false, "User with the given email already exits!"));
        }
        String query = "UPDATE users SET user_email = ? WHERE user_login = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, login);
            statement.executeUpdate();
            return new ArrayList<>(Arrays.asList(true, "Success!"));
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return new ArrayList<>(Arrays.asList(false, "Something went wrong!"));
    }

    /**
     * Searches for a user that matches the given login.
     * @param type Type of the selector, either "email" or "login". Default is "login";
     * @param parameter Parameter based on the selector, either email address or user's login.
     * @return User OBJECT if user exists, otherwise NULL
     */
    public User getUser(String type, String parameter) {
        String query = null;
        if(type.equals("email")) query = "SELECT * FROM users WHERE user_email = ?";
        else query = "SELECT * FROM users WHERE user_login = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) return new User(resultSet.getString("user_login"), resultSet.getString("user_email"));
            return null;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }
}