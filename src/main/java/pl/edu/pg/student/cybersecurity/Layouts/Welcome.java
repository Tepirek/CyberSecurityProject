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
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        welcomeMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(welcomeMessage);
    }
}
