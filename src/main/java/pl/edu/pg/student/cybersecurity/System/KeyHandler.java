package pl.edu.pg.student.cybersecurity.System;

import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter

public class KeyHandler {

    private Integer size;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyHandler(Integer size) {
        this.size = size;
        if(! (boolean) loadKeys(size).get(0)) {
            generateKeys(size);
        }
    }

    private List<Object> generateKeys(Integer size) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(size);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            try (FileOutputStream fileOutputStream = new FileOutputStream("key" + size + ".key")) {
                fileOutputStream.write(privateKey.getEncoded());
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Could not generate the keys!"));
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream("key" + size + ".pub")) {
                fileOutputStream.write(publicKey.getEncoded());
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Could not generate the keys!"));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList(false, "Could not generate the keys!"));
        }
        return new ArrayList<>(Arrays.asList(true, "Success!"));
    }

    private List<Object> loadKeys(Integer size) {
        try {
            File file = new File("key" + size + ".key");
            if(!file.exists()) return new ArrayList<>(Arrays.asList(false, "Could not load the keys!"));
            byte[] bytes = Files.readAllBytes(file.toPath());
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            publicKey = null;
            privateKey = null;
            return new ArrayList<>(Arrays.asList(false, "Could not load the keys!"));
        }
        try {
            File file = new File("key" + size + ".pub");
            if(!file.exists()) return new ArrayList<>(Arrays.asList(false, "Could not load the keys!"));
            byte[] bytes = Files.readAllBytes(file.toPath());
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            publicKey = null;
            privateKey = null;
            return new ArrayList<>(Arrays.asList(false, "Could not load the keys!"));
        }
        return new ArrayList<>(Arrays.asList(true, "Success!"));
    }
}
