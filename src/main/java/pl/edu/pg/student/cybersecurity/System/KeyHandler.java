package pl.edu.pg.student.cybersecurity.System;

import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter

public class KeyHandler {

    private final String owner;
    private final Integer size;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyHandler(String owner, Integer size) {
        this.owner = owner;
        this.size = size;
        List<Object> result = loadKeys(size);
        if(!(boolean) result.get(0)) {
            generateKeys(size);
        } else {
            publicKey = (PublicKey) result.get(1);
            privateKey = (PrivateKey) result.get(2);
        }
    }

    private List<Object> generateKeys(Integer size) {
        String publicKeyName = owner + "_key" + size + ".pub";
        String privateKeyName = owner + "_key" + size + ".key";
        String path = Paths.get("C:/CyberSecurity 1.0/Config/").toString();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(size);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            try (FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(path, privateKeyName).toString())) {
                fileOutputStream.write(privateKey.getEncoded());
                Path filePath = Paths.get(path, privateKeyName);
                Files.setAttribute(filePath, "dos:hidden", true);
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Could not generate the keys!"));
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(path, publicKeyName).toString())) {
                fileOutputStream.write(publicKey.getEncoded());
                Path filePath = Paths.get(path, publicKeyName);
                Files.setAttribute(filePath, "dos:hidden", true);
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
        String publicKeyName = owner + "_key" + size + ".pub";
        String privateKeyName = owner + "_key" + size + ".key";
        String path = Paths.get("C:/CyberSecurity 1.0/Config/").toString();
        try {
            File file = new File(Paths.get(path, privateKeyName).toString());
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
            File file = new File(Paths.get(path, publicKeyName).toString());
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
        return new ArrayList<>(Arrays.asList(true, publicKey, privateKey));
    }
}
