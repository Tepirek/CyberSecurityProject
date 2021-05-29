package pl.edu.pg.student.cybersecurity.System;

import org.apache.commons.io.FilenameUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Decryptor extends Crypto {

    private PrivateKey privateKey;
    private File file;

    public Decryptor(PrivateKey privateKey, File file) {
        this.privateKey = privateKey;
        this.file = file;
    }

    public List<Object> decrypt() {
        List<Object> metadata = readMetadata();
        if(metadata.get(0).getClass() == boolean.class && (boolean) metadata.get(0) == false) return metadata;

        if((Integer) metadata.get(3) == 0) {
            // METADATA: T/F, pKey, size, type
            decryptOnlyRSA();
        } else {
            // METADATA: T/F, pKey, size, type, decryptedSymmetricKey, InitializationVector
            decryptAESRSA((SecretKeySpec) metadata.get(4), (IvParameterSpec) metadata.get(5));
        }
        return null;
    }

    private List<Object> readMetadata() {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] programKeyBuffer = new byte[5];
            fileInputStream.read(programKeyBuffer, 0, 5);
            String programKey = new String(programKeyBuffer, StandardCharsets.UTF_8);
            if(programKey.equals("CSEDP") != true) return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
            // System.out.printf("Program key = %s\n", programKey);
            byte[] sizeBuffer = new byte[4];
            fileInputStream.read(sizeBuffer, 0, 4);
            Integer size = ByteBuffer.wrap(sizeBuffer).getInt();
            if(!Arrays.asList(512, 1024, 2048, 4096).contains(size)) return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
            // System.out.printf("Key size = %d\n", size);
            byte[] typeBuffer = new byte[4];
            fileInputStream.read(typeBuffer, 0, 4);
            Integer type = ByteBuffer.wrap(typeBuffer).getInt();
            if(!Arrays.asList(0, 1).contains(type)) return new ArrayList<>(Arrays.asList(false, "Decryption failed!"));
            // System.out.printf("Type = %d\n", type);
            if(type == 1) {
                byte[] secretKeyBuffer = new byte[size / 8];
                fileInputStream.read(secretKeyBuffer, 0, size / 8);
                SecretKeySpec decryptedSymmetricKey = decryptSymmetricKey(secretKeyBuffer);
                byte[] initializationVector = new byte[16];
                fileInputStream.read(initializationVector, 0, 16);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
                return new ArrayList<>(Arrays.asList(true, programKey, size, type, decryptedSymmetricKey, ivParameterSpec));
                // System.out.printf("SecretKey encrypted = "); print(secretKeyBuffer);
                // System.out.printf("SecretKey after = "); print(decryptedSymmetricKey.getEncoded());
                // System.out.printf("IV after = "); print(initializationVector);
            }
            // System.out.printf("Program key = %s, key size = %d, type = %d\n", programKey, size, type);
            return new ArrayList<>(Arrays.asList(true, programKey, size, type));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Object> decryptOnlyRSA() {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            String baseName = FilenameUtils.getBaseName(file.getPath());
            String extension = FilenameUtils.getExtension(file.getPath());
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 FileOutputStream fileOutputStream = new FileOutputStream("./testing/" + baseName + "_decrypted." + extension)) {
                fileInputStream.skip(13);
                processData(cipher, fileInputStream, fileOutputStream);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Object> decryptAESRSA(SecretKeySpec decryptedSymmetricKey, IvParameterSpec initializationVector) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, decryptedSymmetricKey, initializationVector);
            String baseName = FilenameUtils.getBaseName(file.getPath());
            String extension = FilenameUtils.getExtension(file.getPath());
            try(FileInputStream fileInputStream = new FileInputStream(file);
                FileOutputStream fileOutputStream = new FileOutputStream("./testing/" + baseName + "_decrypted." + extension)) {
                fileInputStream.skip(13 + 128 + 16);
                processData(cipher, fileInputStream, fileOutputStream);
            } catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
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
