package pl.edu.pg.student.cybersecurity.Layouts;

import pl.edu.pg.student.cybersecurity.System.Api;
import pl.edu.pg.student.cybersecurity.System.KeyHandler;
import pl.edu.pg.student.cybersecurity.System.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class Register extends JFrame implements ActionListener{

    private JLabel welcomeMessage = new JLabel("<html>" + "<B>" + "Register to the CyberSecurity!" + "</B>" + "</html>", SwingConstants.CENTER);
    private JLabel info = new JLabel("", SwingConstants.CENTER);
    private JLabel userLabel = new JLabel("Username: ", SwingConstants.CENTER);
    private JLabel passwordLabel1 = new JLabel("Password: ", SwingConstants.CENTER);
    private JLabel passwordLabel2 = new JLabel("Password: ", SwingConstants.CENTER);
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordField1 = new JPasswordField();
    private JPasswordField passwordField2 = new JPasswordField();
    private JButton registerButton = new JButton("Register");
    private JButton resetButton = new JButton("Reset");
    private JButton cancelButton = new JButton("Cancel");
    private Container container = getContentPane();

    public Register() {

        setBounds(0, 0, 500, 400);
        setSize(500, 300);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CyberSecurity - version 1.0");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        container.setLayout(null);

        welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        welcomeMessage.setBounds(65, 35, 200, 50);
        welcomeMessage.setOpaque(true);
        welcomeMessage.setBackground(Color.ORANGE);
        welcomeMessage.setForeground(Color.BLACK);
        container.add(welcomeMessage);


        userLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userLabel.setBounds(65, 85, 100, 30);
        userLabel.setOpaque(true);
        userLabel.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(userLabel);


        userTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userTextField.setBounds(165, 85, 100, 30);
        userTextField.setOpaque(true);
        userTextField.setBackground(Color.ORANGE);
        //userTextField.setForeground(Color.BLACK);
        container.add(userTextField);


        passwordLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLabel1.setBounds(65, 115, 100, 30);
        passwordLabel1.setOpaque(true);
        passwordLabel1.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(passwordLabel1);


        passwordField1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordField1.setBounds(165, 115, 100, 30);
        passwordField1.setOpaque(true);
        passwordField1.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(passwordField1);


        passwordLabel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLabel2.setBounds(65, 145, 100, 30);
        passwordLabel2.setOpaque(true);
        passwordLabel2.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(passwordLabel2);


        passwordField2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordField2.setBounds(165, 145, 100, 30);
        passwordField2.setOpaque(true);
        passwordField2.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(passwordField2);


        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setBounds(65, 175, 100, 30);
        cancelButton.setBackground(Color.ORANGE);
        //loginButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Welcome welcome = new Welcome();
                dispose();
            }
        });
        container.add(cancelButton);


        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resetButton.setBounds(165, 175, 100, 30);
        resetButton.setBackground(Color.ORANGE);
        //loginButton.setForeground(Color.BLACK);
        resetButton.addActionListener(e -> {
            userTextField.setText("");
            passwordField1.setText("");
            passwordField2.setText("");
        });
        container.add(resetButton);


        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        registerButton.setBounds(65, 205, 200, 30);
        registerButton.setBackground(Color.ORANGE);
        //loginButton.setForeground(Color.BLACK);
        registerButton.addActionListener(e -> register());
        container.add(registerButton);


        info.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.setBounds(65, 235, 300, 20);
        info.setOpaque(true);
        info.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        info.setForeground(Color.RED);
        container.add(info);

        setVisible(true);
    }

    public void register() {
        boolean validLogin = true;
        boolean validPassword = true;
        if(userTextField.getText().length() < 5) {
            info.setText("Login must be at least 5 characters long!");
            validLogin = false;
        }
        if (Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword()) != true) {
            info.setText("Passwords aren't equal!");
            passwordField1.setText("");
            passwordField2.setText("");
            validPassword = false;
        }
        if(validLogin && validPassword) {
            String login = userTextField.getText();
            String password = new String(passwordField1.getPassword());
            Api api = new Api();
            List<Object> result = api.insert(login, password);
            if((boolean) result.get(0) == true) {
                User user = (User) api.login(login, password).get(1);
                user.generateKeys();
                Dashboard dashboard = new Dashboard(user);
                dispose();
            } else {
                info.setText((String) result.get(1));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
