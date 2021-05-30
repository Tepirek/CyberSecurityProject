package pl.edu.pg.student.cybersecurity.Layouts;

import pl.edu.pg.student.cybersecurity.System.Api;
import pl.edu.pg.student.cybersecurity.System.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class RegisterPanel extends JPanel implements ActionListener {

    private JLabel welcomeMessage = new JLabel("<html>" + "<B>" + "Register to the CyberSecurity!" + "</B>" + "</html>", SwingConstants.CENTER);
    private JLabel info = new JLabel("", SwingConstants.CENTER);
    private JLabel userLabel = new JLabel("Username: ", SwingConstants.CENTER);
    private JLabel mailLabel = new JLabel("Mail: ", SwingConstants.CENTER);
    private JLabel passwordLabel1 = new JLabel("Password: ", SwingConstants.CENTER);
    private JLabel passwordLabel2 = new JLabel("Password: ", SwingConstants.CENTER);

    private JTextField userTextField = new JTextField();
    private JTextField mailTextField = new JTextField();

    private JPasswordField passwordField1 = new JPasswordField();
    private JPasswordField passwordField2 = new JPasswordField();

    private JButton registerButton = new JButton("Register");
    private JButton resetButton = new JButton("Reset");
    private JButton cancelButton = new JButton("Cancel");

    private WindowApp windowApp;

    public RegisterPanel(WindowApp windowApp) {

        this.windowApp = windowApp;

        setBackground(Color.DARK_GRAY);
        setLayout(null);

        int numberOfElements = 7 + 2;
        int margin = 50;

        int heightElement = (windowApp.getHeight() - 2 * margin) / numberOfElements;
        int widthElement = (windowApp.getWidth() - 2 * margin);
        int counter = 0;

        welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        welcomeMessage.setSize(new Dimension( widthElement, heightElement));
        welcomeMessage.setLocation((windowApp.getWidth() - welcomeMessage.getWidth())/2, margin + counter * heightElement);
        welcomeMessage.setOpaque(true);
        welcomeMessage.setBackground(Color.ORANGE);
        welcomeMessage.setForeground(Color.BLACK);
        add(welcomeMessage);

        counter++;

        userLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userLabel.setSize(new Dimension( widthElement/ 2, heightElement));
        userLabel.setLocation((windowApp.getWidth() - 2 * userLabel.getWidth())/2, margin + counter * heightElement);
        userLabel.setOpaque(true);
        userLabel.setBackground(Color.ORANGE);
        add(userLabel);

        userTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        userTextField.setSize(new Dimension( widthElement/ 2, heightElement));
        userTextField.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        userTextField.setOpaque(true);
        userTextField.setBackground(Color.ORANGE);
        add(userTextField);

        counter++;

        mailLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mailLabel.setSize(new Dimension( widthElement/ 2, heightElement));
        mailLabel.setLocation((windowApp.getWidth() - 2 * mailLabel.getWidth())/2, margin + counter * heightElement);
        mailLabel.setOpaque(true);
        mailLabel.setBackground(Color.ORANGE);
        add(mailLabel);

        mailTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mailTextField.setSize(new Dimension( widthElement/ 2, heightElement));
        mailTextField.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        mailTextField.setOpaque(true);
        mailTextField.setBackground(Color.ORANGE);
        add(mailTextField);

        counter++;

        passwordLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLabel1.setSize(new Dimension( widthElement/ 2, heightElement));
        passwordLabel1.setLocation((windowApp.getWidth() - 2 * passwordLabel1.getWidth())/2, margin + counter * heightElement);
        passwordLabel1.setOpaque(true);
        passwordLabel1.setBackground(Color.ORANGE);
        add(passwordLabel1);

        passwordField1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordField1.setSize(new Dimension( widthElement/2, heightElement));
        passwordField1.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        passwordField1.setOpaque(true);
        passwordField1.setBackground(Color.ORANGE);
        add(passwordField1);

        counter++;

        passwordLabel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLabel2.setSize(new Dimension( widthElement/2, heightElement));
        passwordLabel2.setLocation((windowApp.getWidth() - 2 * passwordLabel2.getWidth())/2, margin + counter * heightElement);
        passwordLabel2.setOpaque(true);
        passwordLabel2.setBackground(Color.ORANGE);
        add(passwordLabel2);

        passwordField2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordField2.setSize(new Dimension( widthElement/2, heightElement));
        passwordField2.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        passwordField2.setOpaque(true);
        passwordField2.setBackground(Color.ORANGE);
        add(passwordField2);

        counter++;

        cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cancelButton.setSize(new Dimension( widthElement / 2, heightElement));
        cancelButton.setLocation((windowApp.getWidth() - 2 * cancelButton.getWidth())/2, margin + counter * heightElement);
        cancelButton.setBackground(Color.ORANGE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        resetButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resetButton.setSize(new Dimension( widthElement / 2, heightElement));
        resetButton.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        resetButton.setBackground(Color.ORANGE);
        resetButton.addActionListener(this);add(resetButton);

        counter++;

        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        registerButton.setSize(new Dimension( widthElement, heightElement));
        registerButton.setLocation((windowApp.getWidth() - registerButton.getWidth())/2, margin + counter * heightElement);
        registerButton.setBackground(Color.ORANGE);
        registerButton.addActionListener(this);
        add(registerButton);

        counter++;

        info.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.setSize(new Dimension( widthElement, heightElement));
        info.setLocation((windowApp.getWidth() - info.getWidth())/2, margin + counter * heightElement);
        info.setOpaque(true);
        info.setBackground(Color.ORANGE);
        info.setForeground(Color.RED);
        add(info);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == cancelButton) { cancel(); }
        if(e.getSource() == registerButton) { register(); }
        if(e.getSource() == resetButton) { reset(); }
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
                windowApp.setUser(user);
                windowApp.getDashboardPanel().toString();
                windowApp.getCardLayout().show(windowApp.getPanelCont(), "DashboardPanel");
            } else {
                info.setText((String) result.get(1));
            }
        }
    }

    public void reset() {

        userTextField.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
    }

    public void cancel() {
        windowApp.getCardLayout().show(windowApp.getPanelCont(), "WelcomPanel");
    }
}
