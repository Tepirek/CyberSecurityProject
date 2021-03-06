package pl.edu.pg.student.cybersecurity.System;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Encryptor extends Crypto {

    private final Integer size;
    private final PublicKey publicKey;
    private final String type;

    /**
     * Constructor for Encryptor class.
     * @param size
     * @param publicKey
     * @param file
     * @param type
     */
    public Encryptor(Integer size, PublicKey publicKey, File file, String type) {
        super(file);
        this.size = size;
        this.publicKey = publicKey;
        this.type = type;
    }

    public List<Object> encrypt() {
        if(type.equals("RSA")) {
            return encryptOnlyRSA();
        } else if(type.equals("AES + RSA")) {
            return encryptAESRSA();
        } else {
            return new ArrayList<>(Arrays.asList(false, "Encryption failed!"));
        }
    }

    private byte[] prepareRSAMetadata() {
        return ByteBuffer.allocate(13)
                .put("CSEDP".getBytes(StandardCharsets.UTF_8))
                .putInt(size)
                .putInt(0)
                .array();
    }

    private byte[] prepareAESRSAMetadata(byte[] encryptedSecretKey, byte[] initializationVector) {
        return ByteBuffer.allocate(13 + (size / 8) + 16)
                .put("CSEDP".getBytes(StandardCharsets.UTF_8))
                .putInt(size)
                .putInt(1)
                .put(encryptedSecretKey)
                .put(initializationVector)
                .array();
    }

    private List<Object> encryptOnlyRSA() {
        StringBuilder stringBuilder = new StringBuilder("<html><b>");
        String fileName = getFileName(size, "RSA", "encrypted");
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                fileOutputStream.write(prepareRSAMetadata());
                processData(cipher, fileInputStream, fileOutputStream);
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
                stringBuilder.append("Encryption failed! Maximum file sizes: <br>");
                stringBuilder.append("<table><tr><td>RSA(512) = 53 bytes</td><td>RSA(1024) = 117 bytes</td></tr>");
                stringBuilder.append("<tr><td>RSA(2048) = 245bytes</td><td>RSA(4096) = 501 bytes</td></tr></table>");
                stringBuilder.append("</b></html>");
                return new ArrayList<>(Arrays.asList(false, stringBuilder.toString()));
            } catch (IOException | BadPaddingException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Encryption failed!"));
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList(false, "Encryption failed!"));
        }
        return new ArrayList<>(Arrays.asList(true, "Success!", fileName));
    }

    private List<Object> encryptAESRSA() {
        String fileName = getFileName(size, "AESRSA", "encrypted");
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] initializationVector = new byte[128 / 8];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(initializationVector);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] encryptedSymmetricKey = encryptSymmetricKey(secretKey);
            try(FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                fileOutputStream.write(prepareAESRSAMetadata(encryptedSymmetricKey, initializationVector));
                processData(cipher, fileInputStream, fileOutputStream);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Encryption failed!"));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList(false, "Encryption failed!"));
        }
        return new ArrayList<>(Arrays.asList(true, "Success!", fileName));
    }

    private byte[] encryptSymmetricKey(SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
