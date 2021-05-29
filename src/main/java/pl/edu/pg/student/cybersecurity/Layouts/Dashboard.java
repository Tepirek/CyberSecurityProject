package pl.edu.pg.student.cybersecurity.Layouts;

import pl.edu.pg.student.cybersecurity.System.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class Dashboard extends JFrame implements ActionListener {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel PKSize = new JLabel("PK size: ", SwingConstants.CENTER);
    private String[] keyValues = { "1024", "2048" };
    private JComboBox keyValuesBox = new JComboBox(keyValues);
    private JLabel encryptionTypeLabel = new JLabel("Encryption type: ", SwingConstants.CENTER);
    private JComboBox encryptionTypesComboBox;
    private String[] usersValues;
    private Map<String, Map<String, Object>> usersKeys = new HashMap<>();
    String[] encryptionTypes = {"RSA", "AES + RSA"};
    private JComboBox userValuesBox;
    private JLabel keyFromUser = new JLabel("PK from: ", SwingConstants.CENTER);
    private JLabel mail = new JLabel("Mail to send: ", SwingConstants.CENTER);
    private JTextField mailTextField = new JTextField();

    private JButton fileButton = new JButton("Choose file");
    private JButton fileButtonDecrypt = new JButton("Choose file");
    private JButton directoryButton = new JButton("Choose directory");
    private JButton encryptButton = new JButton("Encrypt");
    private JButton decryptButton = new JButton("Decrypt");
    private JButton logoutButton = new JButton("Logout");

    private JLabel infoDescrypt = new JLabel("INFO DECRYPT", SwingConstants.CENTER);
    private JLabel infoEncrypt = new JLabel("INFO ENCRYPT", SwingConstants.CENTER);

    private JLabel username = new JLabel("USER NAME", SwingConstants.CENTER);
    private JLabel usermail = new JLabel("USER MAIL", SwingConstants.CENTER);
    private JLabel usernameLabel = new JLabel("Your name: ", SwingConstants.CENTER);
    private JLabel usermailLabel = new JLabel("Your email: ", SwingConstants.CENTER);

    private Container containerEncrypt = new Container();
    private Container containerDecrypt = new Container();
    private Container containerAccounts = new Container();

    private File fileToEncrypt;
    private File fileToDecrypt;
    private File directoryToDecrypt;

    private User user;

    public Dashboard(User user) {

        this.user = user;

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


        encryptionTypesComboBox = new JComboBox(encryptionTypes);
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

        keyFromUser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        keyFromUser.setBounds(65, 0, 100, 30);
        keyFromUser.setOpaque(true);
        keyFromUser.setBackground(Color.ORANGE);
        containerEncrypt.add(keyFromUser);
        Api api = new Api();
        List<User> users = api.getUsers();

        int cnt = 0;
        String[] options = null;
        if(users != null) {
            options = new String[users.size()];
            for(User u : users) {
                options[cnt] = u.getLogin();
                Map<String, Object> params = new HashMap<>();
                params.put("email", u.getEmail());
                params.put("1024", u.getPublicKey1024());
                params.put("2048", u.getPublicKey2048());
                usersKeys.put(u.getLogin(), params);
                cnt++;
            }
        }
        usersValues = options;
        userValuesBox = new JComboBox(usersValues);
        userValuesBox.setBounds(165,0,100,30);
        userValuesBox.setBackground(Color.ORANGE);
        keyValuesBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Color bgColor2 = keyValuesBox.getBackground();
        userValuesBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setBackground(bgColor2);
                super.paint(g);
            }
        });
        containerEncrypt.add(userValuesBox);

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


        infoEncrypt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoEncrypt.setBounds(65, 190, 200, 20);
        infoEncrypt.setOpaque(true);
        infoEncrypt.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        infoEncrypt.setForeground(Color.RED);
        //infoDescrypt.setVisible(false);
        containerEncrypt.add(infoEncrypt);


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

        infoDescrypt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoDescrypt.setBounds(65, 145, 200, 20);
        infoDescrypt.setOpaque(true);
        infoDescrypt.setBackground(Color.ORANGE);
        //userLabel.setForeground(Color.BLACK);
        infoDescrypt.setForeground(Color.RED);
        //infoDescrypt.setVisible(false);
        containerDecrypt.add(infoDescrypt);

//-----------------------------------------------------------------------------------------//

        usernameLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usernameLabel.setBounds(65, 55, 100, 30);
        usernameLabel.setOpaque(true);
        usernameLabel.setBackground(Color.ORANGE);
        containerAccounts.add(usernameLabel);

        username.setBorder((BorderFactory.createLineBorder(Color.BLACK)));
        username.setBounds(165, 55, 100, 30);
        username.setOpaque(true);
        username.setBackground(Color.ORANGE);
        username.setText(user.getLogin());
        containerAccounts.add(username);

        usermailLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usermailLabel.setBounds(65, 85, 100, 30);
        usermailLabel.setOpaque(true);
        usermailLabel.setBackground(Color.ORANGE);
        containerAccounts.add(usermailLabel);

        usermail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usermail.setBounds(165, 85, 100, 30);
        usermail.setOpaque(true);
        usermail.setBackground(Color.ORANGE);
        usermail.setText(user.getEmail());
        containerAccounts.add(usermail);

        logoutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        logoutButton.setBounds(65, 115, 200, 30);
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
        Integer keyIndex = keyValuesBox.getSelectedIndex();
        if(keyIndex < 0) infoEncrypt.setText("Select key size!");
        Integer keySize = Integer.parseInt(keyValues[keyIndex]);
        // System.out.printf("%d => %d\n", keyIndex, keySize);

        Integer recipientIndex = userValuesBox.getSelectedIndex();
        if(recipientIndex < 0) infoEncrypt.setText("Select recipient's name!");
        String recipient = usersValues[recipientIndex];
        // System.out.printf("%d => %s\n", recipientIndex, recipient);

        Integer typeIndex = encryptionTypesComboBox.getSelectedIndex();
        if(typeIndex < 0) infoEncrypt.setText("Select encryption type!");
        String type = encryptionTypes[typeIndex];
        // System.out.printf("%d => %s\n", typeIndex, type);

        if(keyIndex >= 0 && recipientIndex >= 0 && typeIndex >= 0 && fileToEncrypt != null && fileToEncrypt.exists()) {
            PublicKey publicKey;
            if(keySize == 1024) {
                publicKey = (PublicKey) usersKeys.get(recipient).get("1024");
            } else if(keySize == 2048) {
                publicKey = (PublicKey) usersKeys.get(recipient).get("2048");
            } else {
                infoEncrypt.setText("Error occurred!");
                return;
            }
            // System.out.println(publicKey);
            Encryptor encryptor = new Encryptor(keySize, publicKey, fileToEncrypt, type);
            List<Object> result = encryptor.encrypt();
            infoEncrypt.setText((String) result.get(1));
        } else {
            infoEncrypt.setText("Invalid input!");
        }
    }

    public void decrypt() {
        if(fileToDecrypt != null && fileToDecrypt.exists()) {
            Decryptor decryptor = new Decryptor(fileToDecrypt);
            List<Object> result = decryptor.decrypt();
            infoDescrypt.setText((String) result.get(1));
        } else {
            infoDescrypt.setText("Invalid input!");
        }
    }

    public void chooseFile() {

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             this.fileToEncrypt = fc.getSelectedFile();
             this.fileButton.setText(this.fileToEncrypt.getName());
        }
    }

    public void choseFileDecrypt() {

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
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
