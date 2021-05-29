package pl.edu.pg.student.cybersecurity.System;

import lombok.Getter;
import lombok.ToString;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Getter

public class User {

    private String login;
    private String email;
    private PublicKey publicKey1024;
    private PublicKey publicKey2048;

    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }

    public User(String login, String email, byte[] publicKey1024, byte[] publicKey2048) {
        this.login = login;
        this.email = email;
        try {
            if(publicKey1024 != null) this.publicKey1024 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey1024));
            if(publicKey2048 != null) this.publicKey2048 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey2048));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void generateKeys() {
        KeyHandler keyHandler1024 = new KeyHandler(1024);
        KeyHandler keyHandler2048 = new KeyHandler(2048);
        Api api = new Api();
        api.setPublicKey(login, keyHandler1024.getPublicKey().getEncoded(), 1024);
        api.setPublicKey(login, keyHandler2048.getPublicKey().getEncoded(), 2048);
    }

    @Override
    public String toString() {
        return "login: " + login + "\nemail: " + email + "\npublicKey1024: " + publicKey1024.toString() + "\npublicKey2048: " + publicKey2048.toString() + "\n\n";
    }
}
