package pl.edu.pg.student.cybersecurity.System;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Api {

    /**
     * Database's host name
     */
    private final String DB_HOST = "localhost";
    /**
     * Database's name
     */
    private final String DB_NAME = "cybersecurity";
    /**
     * Database's username
     */
    private final String DB_USERNAME = "root";
    /**
     * Database's password
     */
    private final String DB_PASSWORD = "";

    private Connection connection = null;

    /**
     * Default constructor.
     */
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
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    /**
     * Returns list of users from database.
     * @return List of User objects
     */
    public List<User> getUsers() {
        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet resultSet = statement.executeQuery(query)) {
            if(!resultSet.next()) return null;
            resultSet.previous();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("user_login"),
                        resultSet.getString("user_email"),
                        resultSet.getBytes("user_publicKey512"),
                        resultSet.getBytes("user_publicKey1024"),
                        resultSet.getBytes("user_publicKey2048"),
                        resultSet.getBytes("user_publicKey4096")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }

    /**
     * Inserts a new user to the database. Returns TRUE if insertion was successful, otherwise FALSE with adequate error description.
     * @param login user's login
     * @param email user's email
     * @param password user's password
     * @return List of objects
     */
    public List<Object> insert(String login, String email, String password) {
        if(getUser("login", login) != null) {
            return new ArrayList<>(Arrays.asList(false, "Login already exits!"));
        }
        if(getUser("email", email) != null) {
            return new ArrayList<>(Arrays.asList(false, "Email already exits!"));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateString = simpleDateFormat.format(date);
        String query = "INSERT INTO users (user_login, user_email, user_password, created_at) VALUES (?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, dateString);
            statement.executeUpdate();
            return new ArrayList<>(Arrays.asList(true, "Success!"));
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return new ArrayList<>(Arrays.asList(false, "Something went wrong!"));
    }

    /**
     * Updates email of a record from the database that matches the given login. Returns TRUE if update was successful, otherwise FALSE with adequate error description.
     * @param login user's login
     * @param email email to be set
     * @return List of objects
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
            if(resultSet.next()) return new User(resultSet.getString("user_login"),
                    resultSet.getString("user_email"));
            return null;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    /**
     * Performs login of a user. Returns TRUE if login was successful, otherwise FALSE with adequate error description.
     * @param login user's login
     * @param password user's password
     * @return List of objects
     */
    public List<Object> login(String login, String password) {
        if(getUser("login", login) == null) {
            return new ArrayList<>(Arrays.asList(false, "User with the given login doesn't exits!"));
        }
        String query = "SELECT * FROM users WHERE user_login = ? AND user_password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) return new ArrayList<>(Arrays.asList(true, new User(
                    resultSet.getString("user_login"),
                    resultSet.getString("user_email")
            )));
            return new ArrayList<>(Arrays.asList(false, "Incorrect password!"));
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    /**
     * Stores user's public key in the database. Returns TRUE if operation was successful, otherwise FALSE with adequate error description.
     * @param login user's login
     * @param publicKey user's public key
     * @param keySize size of the public key
     * @return List of objects
     */
    public List<Object> setPublicKey(String login, byte[] publicKey, Integer keySize) {
        String query = null;
        if(keySize == 512) {
            query = "UPDATE users SET user_publicKey512 = ? WHERE user_login = ?";
        } else if(keySize == 1024) {
            query = "UPDATE users SET user_publicKey1024 = ? WHERE user_login = ?";
        } else if(keySize == 2048) {
            query = "UPDATE users SET user_publicKey2048 = ? WHERE user_login = ?";
        } else if(keySize == 4096) {
            query = "UPDATE users SET user_publicKey4096 = ? WHERE user_login = ?";
        }
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBytes(1, publicKey);
            statement.setString(2, login);
            statement.executeUpdate();
            return new ArrayList<>(Arrays.asList(true, "Success!"));
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return new ArrayList<>(Arrays.asList(false, "Something went wrong!"));
    }
}