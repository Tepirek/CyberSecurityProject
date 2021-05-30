package pl.edu.pg.student.cybersecurity.Layouts;

import pl.edu.pg.student.cybersecurity.System.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.nio.file.Paths;

public class WelcomePanel extends JPanel implements ActionListener {

    private JLabel welcomeMessage = new JLabel("<html>" + "<b>" + "Welcome to the CyberSecurity!" + "</b>" + "</html>", SwingConstants.CENTER);
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");
    private WindowApp windowApp;

    public WelcomePanel(WindowApp windowApp) {

        this.windowApp = windowApp;

        setBackground(Color.DARK_GRAY);

        setLayout(null);

        int numberOfElements = 3 +  1;
        int margin = 100;

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

        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loginButton.setSize(new Dimension( widthElement, heightElement));
        loginButton.setLocation((windowApp.getWidth() - loginButton.getWidth())/2, margin + counter * heightElement);
        loginButton.setBackground(Color.ORANGE);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        add(loginButton);

        counter++;

        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        registerButton.setSize(new Dimension( widthElement, heightElement));
        registerButton.setLocation((windowApp.getWidth() - registerButton.getWidth())/2, margin + counter * heightElement);
        registerButton.setBackground(Color.ORANGE);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        add(registerButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            windowApp.getCardLayout().show(windowApp.getPanelCont(), "LoginPanel");
        }
        if(e.getSource() == registerButton) {
            windowApp.getCardLayout().show(windowApp.getPanelCont(), "RegisterPanel");
        }
    }
}
