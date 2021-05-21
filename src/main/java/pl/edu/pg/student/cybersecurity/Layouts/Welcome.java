package pl.edu.pg.student.cybersecurity.Layouts;

import javax.swing.*;
import java.awt.*;

public class Welcome extends JFrame {

    private JLabel welcomeMessage = new JLabel("Welcom to the CyberSecurity!", SwingConstants.CENTER);
    private JButton loginButton = new JButton("Logowanie");

    public Welcome() {
        setBounds(0, 0, 500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CryptoSecurity - version 1.0");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.ABOVE_BASELINE;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 20;
        constraints.ipady = 20;

        welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(welcomeMessage, constraints);

        constraints.fill = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        constraints.gridy = 4;
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(loginButton, constraints);
    }
}
