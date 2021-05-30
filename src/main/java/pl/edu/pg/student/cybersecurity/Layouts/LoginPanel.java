package pl.edu.pg.student.cybersecurity.Layouts;

import pl.edu.pg.student.cybersecurity.System.Api;
import pl.edu.pg.student.cybersecurity.System.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginPanel extends JPanel implements ActionListener {

    private JLabel welcomeMessage = new JLabel("<html>" + "<b>" + "Login to the CyberSecurity!" + "</b>" + "</html>", SwingConstants.CENTER);
    private JLabel userLabel = new JLabel("Username: ", SwingConstants.CENTER);
    private JLabel info = new JLabel("", SwingConstants.CENTER);
    private JLabel passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
    private JTextField userTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton resetButton = new JButton("Reset");
    private JButton cancelButton = new JButton("Cancel");

    private WindowApp windowApp;

    public LoginPanel(WindowApp windowApp) {

        this.windowApp = windowApp;

        setBackground(Color.DARK_GRAY);
        setLayout(null);

        int numberOfElements = 6 + 1;
        int margin = 50;

        int heightElement = (windowApp.getHeight() - 2 * margin) / numberOfElements;
        int widthElement = (windowApp.getWidth() - 2 * margin);
        int counter = 0;

        welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        welcomeMessage.setSize(new Dimension( widthElement, heightElement));
        welcomeMessage.setLocation((windowApp.getWidth() - welcomeMessage.getWidth())/2, margin);
        welcomeMessage.setOpaque(true);
        welcomeMessage.setBackground(Color.ORANGE);
        welcomeMessage.setForeground(Color.BLACK);
        add(welcomeMessage);

        counter++;

        userLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userLabel.setSize(new Dimension(widthElement/2, heightElement));
        userLabel.setLocation((windowApp.getWidth() - 2 * userLabel.getWidth())/2, margin + counter * heightElement);
        userLabel.setOpaque(true);
        userLabel.setBackground(Color.ORANGE);
        add(userLabel);

        userTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userTextField.setSize(new Dimension(widthElement/2, heightElement));
        userTextField.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        userTextField.setOpaque(true);
        userTextField.setBackground(Color.ORANGE);
        add(userTextField);

        counter++;

        passwordLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLabel.setSize(new Dimension(widthElement/2, heightElement));
        passwordLabel.setLocation((windowApp.getWidth() -  2 * passwordLabel.getWidth())/2, margin + counter * heightElement);
        passwordLabel.setOpaque(true);
        passwordLabel.setBackground(Color.ORANGE);
        add(passwordLabel);

        passwordField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordField.setSize(new Dimension(widthElement/2, heightElement));
        passwordField.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        passwordField.setOpaque(true);
        passwordField.setBackground(Color.ORANGE);
        add(passwordField);

        counter++;

        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setSize(new Dimension(widthElement/2, heightElement));
        cancelButton.setLocation((windowApp.getWidth() - 2 * cancelButton.getWidth())/2, margin + counter * heightElement);
        cancelButton.setBackground(Color.ORANGE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resetButton.setSize(new Dimension(widthElement/2, heightElement));
        resetButton.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        resetButton.setBackground(Color.ORANGE);
        resetButton.addActionListener(this);
        add(resetButton);

        counter++;

        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loginButton.setSize(new Dimension(widthElement, heightElement));
        loginButton.setLocation((windowApp.getWidth() - loginButton.getWidth())/2, margin + counter * heightElement);
        loginButton.setBackground(Color.ORANGE);
        loginButton.addActionListener(this);
        add(loginButton);

        counter++;

        info.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.setSize(new Dimension(widthElement, heightElement));
        info.setLocation((windowApp.getWidth() - info.getWidth())/2, margin + counter * heightElement);
        info.setOpaque(true);
        info.setBackground(Color.ORANGE);
        info.setForeground(Color.RED);
        add(info);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancelButton) { cancel(); }
        if(e.getSource() == loginButton) { login(); }
        if(e.getSource() == resetButton) { reset(); }
    }

    public void login() {
        boolean validLogin = true;
        boolean validPassword = true;
        StringBuilder stringBuilder = new StringBuilder("<html><b>");

        String login = userTextField.getText();
        if(login.length() < 5 || login.length() > 20) {
            stringBuilder.append("Login is invalid! (5 - 20 characters)<br>");
            validLogin = false;
        }

        String password = new String(passwordField.getPassword());
        if(password.length() < 5 || password.length() > 20) {
            stringBuilder.append("Password is invalid! (5 - 20 characters)<br>");
            validPassword = false;
        }
        if(validLogin && validPassword) {
            Api api = new Api();
            List<Object> result = api.login(login, password);
            if((boolean) result.get(0)) {
                User user = (User) result.get(1);
                user.generateKeys();
                windowApp.setUser(user);
                windowApp.getDashboardPanel().updateAccount();
                windowApp.getCardLayout().show(windowApp.getPanelCont(), "DashboardPanel");
            } else {
                info.setText((String) result.get(1));
            }
        } else {
            stringBuilder.append("</b></html>");
            info.setSize(new Dimension(info.getWidth(), info.getPreferredSize().height));
        }
    }

    public void reset() {
        this.userTextField.setText("");
        this.passwordField.setText("");
        this.info.setText("");
    }

    public void cancel() {
        windowApp.getCardLayout().show(windowApp.getPanelCont(), "WelcomePanel");
    }
}


