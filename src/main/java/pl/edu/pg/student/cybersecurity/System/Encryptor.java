package pl.edu.pg.student.cybersecurity.System;

import org.apache.commons.io.FilenameUtils;
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

    private Integer size;
    private PublicKey publicKey;
    private File file;
    private String type;

    public Encryptor(Integer size, PublicKey publicKey, File file, String type) {
        this.size = size;
        this.publicKey = publicKey;
        this.file = file;
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
        // System.out.printf("SK = %d, IV = %d\n", encryptedSecretKey.length, initializationVector.length);
        // System.out.println(size / 8);
        return ByteBuffer.allocate(13 + (size / 8) + 16)
                .put("CSEDP".getBytes(StandardCharsets.UTF_8))
                .putInt(size)
                .putInt(1)
                .put(encryptedSecretKey)
                .put(initializationVector)
                .array();
    }

    private List<Object> encryptOnlyRSA() {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            String baseName = FilenameUtils.getBaseName(file.getPath());
            String extension = FilenameUtils.getExtension(file.getPath());
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 FileOutputStream fileOutputStream = new FileOutputStream("./testing/" + baseName + "_encrypted." + extension)) {
                fileOutputStream.write(prepareRSAMetadata());
                processData(cipher, fileInputStream, fileOutputStream);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Encryption failed!"));
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList(false, "Encryption failed!"));
        }
        return new ArrayList<>(Arrays.asList(true, "Success!"));
    }

    private List<Object> encryptAESRSA() {
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
            String baseName = FilenameUtils.getBaseName(file.getPath());
            String extension = FilenameUtils.getExtension(file.getPath());
            byte[] encryptedSymmetricKey = encryptSymmetricKey(secretKey);
            System.out.printf("IV before = "); print(initializationVector);
            System.out.printf("SecretKey before = "); print(secretKey.getEncoded());
            System.out.printf("SecretKey encrypted = "); print(encryptedSymmetricKey);
            try(FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream("./testing/" + baseName + "_encrypted." + extension)) {
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
        return new ArrayList<>(Arrays.asList(true, "Success!"));
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
