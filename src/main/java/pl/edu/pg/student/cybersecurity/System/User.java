package pl.edu.pg.student.cybersecurity.System;

import lombok.Getter;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Getter

public class User {

    /**
     * User's login
     */
    private final String login;
    /**
     * User's email
     */
    private final String email;
    /**
     * User's public key of size 512 bit
     */
    private PublicKey publicKey512;
    /**
     * User's public key of size 1024 bit
     */
    private PublicKey publicKey1024;
    /**
     * User's public key of size 2048 bit
     */
    private PublicKey publicKey2048;
    /**
     * User's public key of size 4096 bit
     */
    private PublicKey publicKey4096;

    /**
     * Default constructor.
     * @param login User's login
     * @param email User's email
     */
    public User(String login, String email) {
        this.login = login;
        this.email = email;
    }

    /**
     * Extended constructor.
     * @param login User's login
     * @param email User's email
     * @param publicKey512 User's public key of size 512 bit as byte array
     * @param publicKey1024 User's public key of size 1024 bit byte array
     * @param publicKey2048 User's public key of size 2048 bit byte array
     * @param publicKey4096 User's public key of size 4096 bit byte array
     */
    public User(String login, String email, byte[] publicKey512, byte[] publicKey1024, byte[] publicKey2048, byte[] publicKey4096) {
        this.login = login;
        this.email = email;
        try {
            if(publicKey512 != null) this.publicKey512 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey512));
            if(publicKey1024 != null) this.publicKey1024 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey1024));
            if(publicKey2048 != null) this.publicKey2048 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey2048));
            if(publicKey4096 != null) this.publicKey4096 = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey4096));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function that generates public keys and stores them in the database.
     */
    public void generateKeys() {
        KeyHandler keyHandler512 = new KeyHandler(login, 512);
        KeyHandler keyHandler1024 = new KeyHandler(login, 1024);
        KeyHandler keyHandler2048 = new KeyHandler(login, 2048);
        KeyHandler keyHandler4096 = new KeyHandler(login, 4096);
        Api api = new Api();
        api.setPublicKey(login, keyHandler512.getPublicKey().getEncoded(), 512);
        api.setPublicKey(login, keyHandler1024.getPublicKey().getEncoded(), 1024);
        api.setPublicKey(login, keyHandler2048.getPublicKey().getEncoded(), 2048);
        api.setPublicKey(login, keyHandler4096.getPublicKey().getEncoded(), 4096);
    }

    /**
     * Returns string representation of User object.
     * @return String
     */
    @Override
    public String toString() {
        return "login: " + login + "\nemail: " + email + "\npublicKey1024: " + publicKey1024.toString() + "\npublicKey2048: " + publicKey2048.toString() + "\n\n";
    }
}
