package pl.edu.pg.student.cybersecurity.Layouts;

import lombok.Getter;
import pl.edu.pg.student.cybersecurity.System.Api;
import pl.edu.pg.student.cybersecurity.System.Decryptor;
import pl.edu.pg.student.cybersecurity.System.Encryptor;
import pl.edu.pg.student.cybersecurity.System.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter

public class DashboardPanel extends JPanel implements ActionListener {

    private JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel PKSize = new JLabel("PK size: ", SwingConstants.CENTER);
    private String[] keyValues = { "1024", "2048" };
    private JComboBox keyValuesBox = new JComboBox(keyValues);
    private JLabel encryptionTypeLabel = new JLabel("Encryption type: ", SwingConstants.CENTER);
    private JComboBox encryptionTypesComboBox;
    private String[] usersValues = {};
    private Map<String, Map<String, Object>> usersKeys = new HashMap<>();
    private String[] encryptionTypes = {"RSA", "AES + RSA"};
    private JComboBox userValuesBox;
    private JLabel keyFromUser = new JLabel("PK from: ", SwingConstants.CENTER);
    private JLabel mail = new JLabel("Mail to send: ", SwingConstants.CENTER);
    private JTextField mailTextField = new JTextField();

    private JButton fileButton = new JButton("Choose file");
    private JButton fileButtonDecrypt = new JButton("Choose file");
    private JButton directoryButton = new JButton("Choose directory");
    private JButton directoryEncryptButton = new JButton("Choose directory");
    private JButton encryptButton = new JButton("Encrypt");
    private JButton decryptButton = new JButton("Decrypt");
    private JButton logoutButton = new JButton("Logout");
    private JButton generateKeysButton = new JButton("Generate Keys");

    private JLabel infoDescrypt = new JLabel("", SwingConstants.CENTER);
    private JLabel infoEncrypt = new JLabel("", SwingConstants.CENTER);

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
    private File directoryToEncrypt;

    private final WindowApp windowApp;

    public DashboardPanel(WindowApp windowApp) {

        this.windowApp = windowApp;

        setBackground(Color.DARK_GRAY);
        setLayout(null);

        int numberOfElements = 8 + 2;
        int margin = 50;

        int heightElement = (windowApp.getHeight() - 2 * margin) / numberOfElements;
        int widthElement = (windowApp.getWidth() - 2 * margin);
        int counter = 0;

        containerEncrypt.setLayout(null);
        containerEncrypt.setBackground(Color.ORANGE);

        containerDecrypt.setLayout(null);
        containerDecrypt.setBackground(Color.ORANGE);

        PKSize.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        PKSize.setSize(new Dimension( widthElement/2, heightElement));
        PKSize.setLocation((windowApp.getWidth() - 2 * PKSize.getWidth())/2, margin + counter * heightElement);
        PKSize.setOpaque(true);
        PKSize.setBackground(Color.ORANGE);
        containerEncrypt.add(PKSize);

        keyValuesBox.setBackground(Color.ORANGE);
        keyValuesBox.setSize(new Dimension( widthElement/2, heightElement));
        keyValuesBox.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
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

        counter++;

        encryptionTypeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        encryptionTypeLabel.setSize(new Dimension( widthElement/2, heightElement));
        encryptionTypeLabel.setLocation((windowApp.getWidth() - 2 * encryptionTypeLabel.getWidth())/2, margin + counter * heightElement);
        encryptionTypeLabel.setOpaque(true);
        encryptionTypeLabel.setBackground(Color.ORANGE);
        containerEncrypt.add(encryptionTypeLabel);

        encryptionTypesComboBox = new JComboBox(encryptionTypes);
        encryptionTypesComboBox.setSize(new Dimension( widthElement/2, heightElement));
        encryptionTypesComboBox.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
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

        counter++;

        keyFromUser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        keyFromUser.setSize(new Dimension( widthElement/2, heightElement));
        keyFromUser.setLocation((windowApp.getWidth() - 2 * keyFromUser.getWidth())/2, margin + counter * heightElement);
        keyFromUser.setOpaque(true);
        keyFromUser.setBackground(Color.ORANGE);
        containerEncrypt.add(keyFromUser);

        Api api = new Api();
        List<User> users = api.getUsers();

        int cnt = 0;
        String[] options;
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
            usersValues = options;
        }

        userValuesBox = new JComboBox(usersValues);
        userValuesBox.setSize(new Dimension( widthElement/2, heightElement));
        userValuesBox.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
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
        userValuesBox.addActionListener(e -> {
            int recipientIndex = userValuesBox.getSelectedIndex();
            String recipient = usersValues[recipientIndex];
            mailTextField.setText((String) usersKeys.get(recipient).get("email"));
        });
        containerEncrypt.add(userValuesBox);

        counter++;

        mail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mail.setSize(new Dimension( widthElement/2, heightElement));
        mail.setLocation((windowApp.getWidth() - 2 * PKSize.getWidth())/2, margin + counter * heightElement);
        mail.setOpaque(true);
        mail.setBackground(Color.ORANGE);
        containerEncrypt.add(mail);

        mailTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mailTextField.setSize(new Dimension( widthElement/2, heightElement));
        mailTextField.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        mailTextField.setOpaque(true);
        mailTextField.setBackground(Color.ORANGE);
        containerEncrypt.add(mailTextField);

        counter++;

        fileButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fileButton.setSize(new Dimension( widthElement, heightElement));
        fileButton.setLocation((windowApp.getWidth() - fileButton.getWidth())/2, margin + counter * heightElement);
        fileButton.setBackground(Color.ORANGE);
        fileButton.addActionListener(this);
        containerEncrypt.add(fileButton);

        counter++;

        directoryEncryptButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        directoryEncryptButton.setSize(new Dimension( widthElement, heightElement));
        directoryEncryptButton.setLocation((windowApp.getWidth() - directoryEncryptButton.getWidth())/2, margin + counter * heightElement);
        directoryEncryptButton.setBackground(Color.ORANGE);
        directoryEncryptButton.addActionListener(this);
        containerEncrypt.add(directoryEncryptButton);

        counter++;

        encryptButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        encryptButton.setSize(new Dimension( widthElement, heightElement));
        encryptButton.setLocation((windowApp.getWidth() - encryptButton.getWidth())/2, margin + counter * heightElement);
        encryptButton.setBackground(Color.ORANGE);
        encryptButton.addActionListener(this);
        containerEncrypt.add(encryptButton);

        counter++;

        infoEncrypt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoEncrypt.setSize(new Dimension( widthElement, heightElement));
        infoEncrypt.setLocation((windowApp.getWidth() - infoEncrypt.getWidth())/2, margin + counter * heightElement);
        infoEncrypt.setOpaque(true);
        infoEncrypt.setBackground(Color.ORANGE);
        infoEncrypt.setForeground(Color.RED);
        containerEncrypt.add(infoEncrypt);

//-----------------------------------------------------------------------------------------//

        numberOfElements = 4 + 2;
        margin = 50;

        heightElement = (windowApp.getHeight() - 2 * margin) / numberOfElements;
        widthElement = (windowApp.getWidth() - 2 * margin);
        counter = 0;

        fileButtonDecrypt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fileButtonDecrypt.setSize(new Dimension( widthElement, heightElement));
        fileButtonDecrypt.setLocation((windowApp.getWidth() - fileButtonDecrypt.getWidth())/2, margin + counter * heightElement);
        fileButtonDecrypt.setBackground(Color.ORANGE);
        fileButtonDecrypt.addActionListener(this);
        containerDecrypt.add(fileButtonDecrypt);

        counter++;

        directoryButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        directoryButton.setSize(new Dimension( widthElement, heightElement));
        directoryButton.setLocation((windowApp.getWidth() - directoryButton.getWidth())/2, margin + counter * heightElement);
        directoryButton.setBackground(Color.ORANGE);
        directoryButton.addActionListener(this);
        containerDecrypt.add(directoryButton);

        counter++;

        decryptButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        decryptButton.setSize(new Dimension( widthElement, heightElement));
        decryptButton.setLocation((windowApp.getWidth() - decryptButton.getWidth())/2, margin + counter * heightElement);
        decryptButton.setBackground(Color.ORANGE);
        decryptButton.addActionListener(this);
        containerDecrypt.add(decryptButton);

        counter++;

        infoDescrypt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoDescrypt.setSize(new Dimension( widthElement, heightElement));
        infoDescrypt.setLocation((windowApp.getWidth() - infoDescrypt.getWidth())/2, margin + counter * heightElement);
        infoDescrypt.setOpaque(true);
        infoDescrypt.setBackground(Color.ORANGE);
        infoDescrypt.setForeground(Color.RED);
        containerDecrypt.add(infoDescrypt);

//-----------------------------------------------------------------------------------------//

        numberOfElements = 4 + 2;
        margin = 50;

        heightElement = (windowApp.getHeight() - 2 * margin) / numberOfElements;
        widthElement = (windowApp.getWidth() - 2 * margin);
        counter = 0;

        usernameLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usernameLabel.setSize(new Dimension( widthElement/2, heightElement));
        usernameLabel.setLocation((windowApp.getWidth() - 2 * usernameLabel.getWidth())/2, margin + counter * heightElement);
        usernameLabel.setOpaque(true);
        usernameLabel.setBackground(Color.ORANGE);
        containerAccounts.add(usernameLabel);

        username.setBorder((BorderFactory.createLineBorder(Color.BLACK)));
        username.setSize(new Dimension( widthElement/2, heightElement));
        username.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        username.setOpaque(true);
        username.setBackground(Color.ORANGE);
        containerAccounts.add(username);

        counter++;

        usermailLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usermailLabel.setSize(new Dimension( widthElement/2, heightElement));
        usermailLabel.setLocation((windowApp.getWidth() - 2 * usermailLabel.getWidth())/2, margin + counter * heightElement);
        usermailLabel.setOpaque(true);
        usermailLabel.setBackground(Color.ORANGE);
        containerAccounts.add(usermailLabel);

        usermail.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usermail.setSize(new Dimension( widthElement/2, heightElement));
        usermail.setLocation(windowApp.getWidth()/2, margin + counter * heightElement);
        usermail.setOpaque(true);
        usermail.setBackground(Color.ORANGE);
        containerAccounts.add(usermail);

        counter++;

        generateKeysButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        generateKeysButton.setSize(new Dimension( widthElement, heightElement));
        generateKeysButton.setLocation((windowApp.getWidth() - generateKeysButton.getWidth())/2, margin + counter * heightElement);
        generateKeysButton.setBackground(Color.ORANGE);
        generateKeysButton.addActionListener(this);
        containerAccounts.add(generateKeysButton);

        counter++;

        logoutButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        logoutButton.setSize(new Dimension( widthElement, heightElement));
        logoutButton.setLocation((windowApp.getWidth() - logoutButton.getWidth())/2, margin + counter * heightElement);
        logoutButton.setBackground(Color.ORANGE);
        logoutButton.addActionListener(this);
        containerAccounts.add(logoutButton);

        //-----------------------------------------------------------------------------------------//

        tabbedPane.setSize(windowApp.getWidth() - 1, windowApp.getHeight() - 1);
        tabbedPane.addTab("Encrypt", containerEncrypt);
        tabbedPane.addTab("Decrypt", containerDecrypt);
        tabbedPane.addTab("Account", containerAccounts);
        tabbedPane.setBackground(Color.ORANGE);
        add(tabbedPane);
    }

    public void updateAccount() {
        username.setText(windowApp.getUser().getLogin());
        usermail.setText(windowApp.getUser().getEmail());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fileButton) { chooseFile(); }
        if(e.getSource() == encryptButton) { encrypt(); }
        if(e.getSource() == fileButtonDecrypt) { choseFileDecrypt(); }
        if(e.getSource() == directoryButton) { chooseDirectory(); }
        if(e.getSource() == directoryEncryptButton) { chooseDirectoryToSaveEncrypt();}
        if(e.getSource() == decryptButton) { decrypt(); }
        if(e.getSource() == logoutButton) { logout(); }
        if(e.getSource() == generateKeysButton) { generateKeys(); }
    }

    public void generateKeys() {
        generateKeysButton.setText("Keys generated!");
        generateKeysButton.setForeground(Color.GREEN);
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
        fc.setCurrentDirectory(new File(Paths.get("C:/Cybersecurity 1.0/Encrypted Files").toString()));
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

    public void chooseDirectoryToSaveEncrypt() {

        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            this.directoryToEncrypt = fc.getSelectedFile();
            this.directoryEncryptButton.setText(this.directoryToEncrypt.getName());
        }
    }

    public void decrypt() {

        if(fileToDecrypt != null && fileToDecrypt.exists()) {
            Decryptor decryptor = new Decryptor(windowApp.getUser(), fileToDecrypt);
            List<Object> result = decryptor.decrypt();
            infoDescrypt.setText((String) result.get(1));
        } else {
            infoDescrypt.setText("Invalid input!");
        }
    }

    public void encrypt() {
        boolean validKeyIndex = true;
        boolean validRecipientIndex = true;
        boolean validTypeIndex = true;
        boolean validKeySize = true;
        boolean validFile = true;
        Integer keySize = -1;
        String type = "";
        String recipient = "";
        StringBuilder stringBuilder = new StringBuilder("<html><b>");

        Integer keyIndex = keyValuesBox.getSelectedIndex();
        if(keyIndex < 0) {
            stringBuilder.append("Select key size!<br>");
            validKeyIndex = false;
        } else {
            keySize = Integer.parseInt(keyValues[keyIndex]);
            if(keySize != 1024 && keySize != 2048) {
                stringBuilder.append("Key size is invalid!<br>");
                validKeySize = false;
            }
        }

        Integer typeIndex = encryptionTypesComboBox.getSelectedIndex();
        if(typeIndex < 0) {
            stringBuilder.append("Select encryption type!<br>");
            validTypeIndex = false;
        } else {
            type = encryptionTypes[typeIndex];
        }

        Integer recipientIndex = userValuesBox.getSelectedIndex();
        if(recipientIndex < 0) {
            stringBuilder.append("Select recipient's name!<br>");
            validRecipientIndex = false;
        } else {
            recipient = usersValues[recipientIndex];
        }

        if(this.fileToEncrypt == null || !this.fileToEncrypt.exists()) {
            stringBuilder.append("Select a valid file!");
            validFile = false;
        }

        if(validKeyIndex && validKeySize && validTypeIndex && validRecipientIndex && validFile) {
            PublicKey publicKey = (PublicKey) usersKeys.get(recipient).get(String.valueOf(keySize));
            Encryptor encryptor = new Encryptor(keySize, publicKey, fileToEncrypt, type);
            List<Object> result = encryptor.encrypt();
            infoEncrypt.setText((String) result.get(1));
        } else {
            stringBuilder.append("</b></html>");
            infoEncrypt.setText(stringBuilder.toString());
            infoEncrypt.setSize(new Dimension(infoEncrypt.getWidth(), infoEncrypt.getPreferredSize().height));
        }
    }

    public void logout() {
        windowApp.getCardLayout().show(windowApp.getPanelCont(), "WelcomePanel");
    }
}
