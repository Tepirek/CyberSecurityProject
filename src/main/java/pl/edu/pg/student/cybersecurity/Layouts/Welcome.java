package pl.edu.pg.student.cybersecurity.Layouts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Welcome extends JFrame implements ActionListener {

    private JLabel welcomeMessage = new JLabel("<html>" + "<B>" + "Welcom to the CyberSecurity!" + "</B>" + "</html>", SwingConstants.CENTER);
    private JButton loginButton = new JButton("Login");
    private JButton registerButton = new JButton("Register");

    public Welcome() {

        setBounds(0, 0, 500, 400);
        setSize(330, 300);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CyberSecurity - version 1.0");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 20;
        constraints.ipady = 20;

        welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        welcomeMessage.setPreferredSize(new Dimension(200, 30));
        welcomeMessage.setOpaque(true);
        welcomeMessage.setBackground(Color.ORANGE);
        welcomeMessage.setForeground(Color.BLACK);
        this.add(welcomeMessage, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipadx = 20;
        constraints.ipady = 20;

        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loginButton.setPreferredSize(new Dimension(200, 15));
        loginButton.setBackground(Color.ORANGE);
        loginButton.setFocusable(false);
        //loginButton.setForeground(Color.BLACK);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                dispose();
            }
        });

        this.add(loginButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.ipadx = 20;
        constraints.ipady = 20;

        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        registerButton.setPreferredSize(new Dimension(200, 15));
        registerButton.setBackground(Color.ORANGE);
        //registerButton.setForeground(Color.BLACK);
        registerButton.setFocusable(false);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                dispose();
            }
        });
        this.add(registerButton, constraints);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
