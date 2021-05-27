package pl.edu.pg.student.cybersecurity.System;

public class User {

    private String login;
    private String email;

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
