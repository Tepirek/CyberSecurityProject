package pl.edu.pg.student.cybersecurity;

import pl.edu.pg.student.cybersecurity.Layouts.Login;
import pl.edu.pg.student.cybersecurity.Layouts.Register;
import pl.edu.pg.student.cybersecurity.Layouts.Welcome;
import pl.edu.pg.student.cybersecurity.System.Api;
import pl.edu.pg.student.cybersecurity.System.Encryptor;
import pl.edu.pg.student.cybersecurity.System.KeyHandler;
import pl.edu.pg.student.cybersecurity.System.User;

import java.io.File;

public class App {

    public static void main(String[] args) {

        // Welcome welcome = new Welcome();

        KeyHandler keyHandler = new KeyHandler(512);
        File file = new File("test2.txt");

        Encryptor encryptor = new Encryptor(512, keyHandler.getPublicKey(), file);
        encryptor.encrypt("OnlyRSA");

    }
    
}

