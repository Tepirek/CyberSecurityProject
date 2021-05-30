package pl.edu.pg.student.cybersecurity.System;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Decryptor extends Crypto {

    private final User user;
    private PrivateKey privateKey;

    public Decryptor(User user, File file) {
        super(file);
        this.user = user;
    }

    public List<Object> decrypt() {
        List<Object> metadata = readMetadata();
        if(!((boolean) metadata.get(0))) return metadata;

        if((Integer) metadata.get(3) == 0) {
            // METADATA: T/F, pKey, size, type
            return decryptOnlyRSA((Integer) metadata.get(2));
        } else if((Integer) metadata.get(3) == 1) {
            // METADATA: T/F, pKey, size, type, decryptedSymmetricKey, InitializationVector
            return decryptAESRSA((Integer) metadata.get(2), (SecretKeySpec) metadata.get(4), (IvParameterSpec) metadata.get(5));
        } else {
            return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
        }
    }

    private List<Object> readMetadata() {
        boolean validProgramKey = true;
        boolean validKeySize = true;
        boolean validEncryptionType = true;
        StringBuilder stringBuilder = new StringBuilder("<html><b>");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] programKeyBuffer = new byte[5];
            fileInputStream.read(programKeyBuffer, 0, 5);
            String programKey = new String(programKeyBuffer, StandardCharsets.UTF_8);
            if(!programKey.equals("CSEDP")) {
                stringBuilder.append("Decryption failed, couldn't read program key!<br>");
                validProgramKey = false;
            }

            byte[] sizeBuffer = new byte[4];
            fileInputStream.read(sizeBuffer, 0, 4);
            Integer size = ByteBuffer.wrap(sizeBuffer).getInt();
            if(!Arrays.asList(512, 1024, 2048, 4096).contains(size)) {
                stringBuilder.append("Decryption failed, couldn't read key size!<br>");
                validKeySize = false;
            }

            byte[] typeBuffer = new byte[4];
            fileInputStream.read(typeBuffer, 0, 4);
            Integer type = ByteBuffer.wrap(typeBuffer).getInt();
            if(!Arrays.asList(0, 1).contains(type)) {
                stringBuilder.append("Decryption failed, couldn't read encryption type!<br>");
                validEncryptionType = false;
            }

            if(validProgramKey && validKeySize && validEncryptionType) {
                if(type == 1) {
                    byte[] secretKeyBuffer = new byte[size / 8];
                    fileInputStream.read(secretKeyBuffer, 0, size / 8);
                    KeyHandler keyHandler = new KeyHandler(user.getLogin(), size);
                    this.privateKey = keyHandler.getPrivateKey();
                    SecretKeySpec decryptedSymmetricKey = decryptSymmetricKey(secretKeyBuffer);
                    byte[] initializationVector = new byte[16];
                    fileInputStream.read(initializationVector, 0, 16);
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
                    return new ArrayList<>(Arrays.asList(true, programKey, size, type, decryptedSymmetricKey, ivParameterSpec));
                }
                return new ArrayList<>(Arrays.asList(true, programKey, size, type));
            } else {
                stringBuilder.append("</b></html>");
                return new ArrayList<>(Arrays.asList(false, stringBuilder.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
    }

    private List<Object> decryptOnlyRSA(Integer size) {
        KeyHandler keyHandler = new KeyHandler(user.getLogin(), size);
        this.privateKey = keyHandler.getPrivateKey();
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 FileOutputStream fileOutputStream = new FileOutputStream(getFileName(size, "RSA", "decrypted"))) {
                fileInputStream.skip(13);
                processData(cipher, fileInputStream, fileOutputStream);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
        }
        return new ArrayList<>(Arrays.asList(true, "Success!"));
    }

    private List<Object> decryptAESRSA(Integer size, SecretKeySpec decryptedSymmetricKey, IvParameterSpec initializationVector) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, decryptedSymmetricKey, initializationVector);
            try(FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream(getFileName(size, "AESRSA", "decrypted"))) {
                fileInputStream.skip(13 + (size / 8) + 16);
                processData(cipher, fileInputStream, fileOutputStream);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
                return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
        }
        return new ArrayList<>(Arrays.asList(true, "Success!"));
    }

    private SecretKeySpec decryptSymmetricKey(byte[] encryptedSymmetricKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedSymmetricKey = cipher.doFinal(encryptedSymmetricKey);
            return new SecretKeySpec(decryptedSymmetricKey, "AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
