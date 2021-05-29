package pl.edu.pg.student.cybersecurity;

import pl.edu.pg.student.cybersecurity.Layouts.Dashboard;
import pl.edu.pg.student.cybersecurity.Layouts.Login;
import pl.edu.pg.student.cybersecurity.Layouts.Register;
import pl.edu.pg.student.cybersecurity.Layouts.Welcome;
import pl.edu.pg.student.cybersecurity.System.Api;
import pl.edu.pg.student.cybersecurity.System.Decryptor;
import pl.edu.pg.student.cybersecurity.System.Encryptor;
import pl.edu.pg.student.cybersecurity.System.KeyHandler;

import java.io.File;

public class App {

    public static void main(String[] args) {

        // Welcome welcome = new Welcome();
        // Dashboard dashboard = new Dashboard();

        Integer size = 2048;
        File file1 = new File("./testing/test1RSA.txt");
        File file2 = new File("./testing/test1AESRSA.txt");
        KeyHandler keyHandler = new KeyHandler(size);

        Encryptor encryptor1 = new Encryptor(size, keyHandler.getPublicKey(), file1, "RSA");
        encryptor1.encrypt();
        Encryptor encryptor2 = new Encryptor(size, keyHandler.getPublicKey(), file2, "AESRSA");
        encryptor2.encrypt();

        File file3 = new File("./testing/test1RSA_encrypted.txt");
        File file4 = new File("./testing/test1AESRSA_encrypted.txt");
        Decryptor decryptor1 = new Decryptor(keyHandler.getPrivateKey(), file3);
        decryptor1.decrypt();
        Decryptor decryptor2 = new Decryptor(keyHandler.getPrivateKey(), file4);
        decryptor2.decrypt();
    }
    
}