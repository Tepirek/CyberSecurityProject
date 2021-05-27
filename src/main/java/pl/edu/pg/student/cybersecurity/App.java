package pl.edu.pg.student.cybersecurity;

import pl.edu.pg.student.cybersecurity.System.Decryptor;
import pl.edu.pg.student.cybersecurity.System.KeyHandler;

import java.io.File;
import java.security.Key;

public class App {

    public static void main(String[] args) {

        // Welcome welcome = new Welcome();
        Integer size = 512;
        File file = new File("./testing/test2_encrypted.txt");
        KeyHandler keyHandler = new KeyHandler(size);

        Decryptor decryptor = new Decryptor(keyHandler.getPrivateKey(), file);
        decryptor.decrypt();
    }
    
}

