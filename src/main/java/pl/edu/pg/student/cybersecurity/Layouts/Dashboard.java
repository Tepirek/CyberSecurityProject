package pl.edu.pg.student.cybersecurity.Layouts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static java.lang.System.exit;

public class Dashboard extends JFrame implements ActionListener {

    JTabbedPane tabbedPane = new JTabbedPane();

    JLabel PKSize = new JLabel("PK size: ", SwingConstants.CENTER);
    JLabel encryptionTypeLabel = new JLabel("Encryption type: ", SwingConstants.CENTER);

    JLabel mail = new JLabel("Mail to send: ", SwingConstants.CENTER);
    JTextField mailTextField = new JTextField();

    JButton fileButton = new JButton("Choose file");
    JButton fileButtonDecrypt = new JButton("Choose file");
    JButton directoryButton = new JButton("Choose directory");
    JButton encryptButton = new JButton("Encrypt");
    JButton decryptButton = new JButton("Decrypt");
    JButton logoutButton = new JButton("Logout");

    Container containerEncrypt = new Container();
    Container containerDecrypt = new Container();
    Container containerAccounts = new Container();

    File fileToEncrypt;
    File fileToDecrypt;
    File directoryToDecrypt;

    public Dashboard() {

        containerEncrypt.setLayout(null);
        containerEncrypt.setBackground(Color.ORANGE);

        containerDecrypt.setLayout(null);
        containerDecrypt.setBackground(Color.ORANGE);

        containerAccounts.setLayout(null);
        containerAccounts.setBackground(Color.ORANGE);


        setBounds(0, 0, 500, 400);
        setSize(330, 300);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("CyberSecurity - version 1.0");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

//-----------------------------------------------------------------------------------------//

        PKSize.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        PKSize.setBounds(65, 40, 100, 30);
        PKSize.setOpaque(true);
        PKSize.setBackground(Color.ORANGE);
        containerEncrypt.add(PKSize);


        String[] keyValues = {"1024","2048","4096"};
        JComboBox keyValuesBox = new JComboBox(keyValues);
        keyValuesBox.setBounds(165,40,100,30);
        keyValuesBox.setBackground(Color.ORANGE);
        keyValuesBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Color bgColor = keyValuesBox.getBackground();
        keyValuesBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setBackground(bgColor);
                super.paint(g);
            }
        });
        containerEncrypt.add(keyValuesBox);


        encryptionTypeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        encryptionTypeLabel.setBounds(65, 70, 100, 30);
        encryptionTypeLabel.setOpaque(true);
        encryptionTypeLabel.setBackground(Color.ORANGE);
        containerEncrypt.add(encryptionTypeLabel);


        String[] encryptionTypes = {"RSA", "AES + RSA"};
        JComboBox encryptionTypesComboBox = new JComboBox(encryptionTypes);
        encryptionTypesComboBox.setBounds(165,70,100,30);
        encryptionTypesComboBox.setBackground(Color.ORANGE);
        encryptionTypesComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        encryptionTypesComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setBackground(bgColor);
                super.paint(g);
            }
        });
        containerEncrypt.add(encryptionTypesComboBox);


        mail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mail.setBounds(65, 100, 100, 30);
        mail.setOpaque(true);
        mail.setBackground(Color.ORANGE);
        containerEncrypt.add(mail);


        mailTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mailTextField.setBounds(165, 100, 100, 30);
        mailTextField.setOpaque(true);
        mailTextField.setBackground(Color.ORANGE);
        containerEncrypt.add(mailTextField);


        fileButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fileButton.setBounds(65, 130, 200, 30);
        fileButton.setBackground(Color.ORANGE);
        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });
        containerEncrypt.add(fileButton);


        encryptButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        encryptButton.setBounds(65, 160, 200, 30);
        encryptButton.setBackground(Color.ORANGE);
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                encrypt();
            }
        });
        containerEncrypt.add(encryptButton);

//-----------------------------------------------------------------------------------------//

        fileButtonDecrypt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fileButtonDecrypt.setBounds(65, 55, 200, 30);
        fileButtonDecrypt.setBackground(Color.ORANGE);
        fileButtonDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                choseFileDecrypt();
            }
        });
        containerDecrypt.add(fileButtonDecrypt);

        directoryButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        directoryButton.setBounds(65, 85, 200, 30);
        directoryButton.setBackground(Color.ORANGE);
        directoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseDirectory();
            }
        });
        containerDecrypt.add(directoryButton);

        decryptButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        decryptButton.setBounds(65, 115, 200, 30);
        decryptButton.setBackground(Color.ORANGE);
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decrypt();
            }
        });
        containerDecrypt.add(decryptButton);

//-----------------------------------------------------------------------------------------//


        logoutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        logoutButton.setBounds(65, 85, 200, 40);
        logoutButton.setBackground(Color.ORANGE);
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        containerAccounts.add(logoutButton);


//-----------------------------------------------------------------------------------------//

        tabbedPane.setBounds(0, 0, 499, 399);
        tabbedPane.addTab("Encrypt", containerEncrypt);
        tabbedPane.addTab("Decrypt", containerDecrypt);
        tabbedPane.addTab("Account", containerAccounts);
        tabbedPane.setBackground(Color.ORANGE);
        add(tabbedPane);

        setVisible(true);
    }

    public void encrypt() {

    }

    public void decrypt() {

    }

    public void chooseFile() {

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             this.fileToEncrypt = fc.getSelectedFile();
             this.fileButton.setText(this.fileToEncrypt.getName());
        }
    }

    public void choseFileDecrypt() {

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            this.fileToDecrypt = fc.getSelectedFile();
            this.fileButtonDecrypt.setText(this.fileToDecrypt.getName());
        }
    }

    public void chooseDirectory() {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            this.directoryToDecrypt = fc.getSelectedFile();
            this.directoryButton.setText(this.directoryToDecrypt.getName());
        }
    }

    public void logout() {

        Welcome welcome = new Welcome();
        dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
