package pl.edu.pg.student.cybersecurity;

import pl.edu.pg.student.cybersecurity.Layouts.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {

        try {
            Files.createDirectories(Paths.get("C:/CyberSecurity 1.0/Assets"));
            Files.createDirectories(Paths.get("C:/CyberSecurity 1.0/Config"));
            Files.createDirectories(Paths.get("C:/CyberSecurity 1.0/Encrypted Files"));
            Files.createDirectories(Paths.get("C:/CyberSecurity 1.0/Decrypted Files"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        WindowApp windowApp = new WindowApp();
    }
}