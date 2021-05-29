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

        Welcome welcome = new Welcome();
        // Dashboard dashboard = new Dashboard();

//        Integer size = 512;
//        File file = new File("./testing/test1.txt");
//        File file2 = new File("./testing/test1_encrypted.txt");
//        KeyHandler keyHandler = new KeyHandler(size);
//
//        Encryptor encryptor = new Encryptor(size, keyHandler.getPublicKey(), file, "RSA");
//        encryptor.encrypt();
//
//        Decryptor decryptor = new Decryptor(keyHandler.getPrivateKey(), file2);
//        decryptor.decrypt();
    }
    
}