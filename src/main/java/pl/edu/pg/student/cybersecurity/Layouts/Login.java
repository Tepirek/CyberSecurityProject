package pl.edu.pg.student.cybersecurity.Layouts;

import pl.edu.pg.student.cybersecurity.System.Api;
import pl.edu.pg.student.cybersecurity.System.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.List;

import static java.lang.Thread.sleep;

public class Login extends JFrame implements ActionListener {

    private JLabel welcomeMessage = new JLabel("<html>" + "<B>" + "Login to the CyberSecurity!" + "</B>" + "</html>", SwingConstants.CENTER);
    private JLabel userLabel = new JLabel("Username: ", SwingConstants.CENTER);
    private JLabel info = new JLabel("", SwingConstants.CENTER);
    private JLabel passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton resetButton = new JButton("Reset");
    private JButton cancelButton = new JButton("Cancel");
    private Container container = getContentPane();

    public Login() {

        setBounds(0, 0, 500, 400);
        setSize(500, 300);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CyberSecurity - version 1.0");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        container.setLayout(null);

        welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        welcomeMessage.setBounds(65, 50, 200, 50);
        welcomeMessage.setOpaque(true);
        welcomeMessage.setBackground(Color.ORANGE);
        welcomeMessage.setForeground(Color.BLACK);
        container.add(welcomeMessage);


        userLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userLabel.setBounds(65, 100, 100, 30);
        userLabel.setOpaque(true);
        userLabel.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(userLabel);


        userTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userTextField.setBounds(165, 100, 100, 30);
        userTextField.setOpaque(true);
        userTextField.setBackground(Color.ORANGE);
        //userTextField.setForeground(Color.BLACK);
        container.add(userTextField);


        passwordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLabel.setBounds(65, 130, 100, 30);
        passwordLabel.setOpaque(true);
        passwordLabel.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(passwordLabel);


        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordField.setBounds(165, 130, 100, 30);
        passwordField.setOpaque(true);
        passwordField.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        container.add(passwordField);

        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setBounds(65, 160, 100, 30);
        cancelButton.setBackground(Color.ORANGE);
        //cancelButton.setForeground(Color.BLACK);
        cancelButton.addActionListener(e -> {
            Welcome welcome = new Welcome();
            dispose();
        });
        container.add(cancelButton);

        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resetButton.setBounds(165, 160, 100, 30);
        resetButton.setBackground(Color.ORANGE);
        //loginButton.setForeground(Color.BLACK);
        resetButton.addActionListener(e -> {
            userTextField.setText("");
            passwordField.setText("");
        });
        container.add(resetButton);


        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loginButton.setBounds(65, 190, 200, 30);
        loginButton.setBackground(Color.ORANGE);
        //loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(e -> login());
        container.add(loginButton);


        info.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.setBounds(65, 220, 300, 30);
        info.setOpaque(true);
        info.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        info.setForeground(Color.RED);
        container.add(info);

        setVisible(true);
    }

    public void login() {
        String login = userTextField.getText();
        String password = new String(passwordField.getPassword());
        System.out.printf("Login = %s, Password = %s\n", login, password);
        Api api = new Api();
        List<Object> result = api.login(login, password);
        if((boolean) result.get(0)) {
            User user = (User) result.get(1);
            user.generateKeys();
            Dashboard dashboard = new Dashboard(user);
            dispose();
        } else {
            info.setText((String) result.get(1));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
