package pl.edu.pg.student.cybersecurity.System;

import org.apache.commons.io.FilenameUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Encryptor {

    private Integer size;
    private PublicKey publicKey;
    private File file;

    public Encryptor(Integer size, PublicKey publicKey, File file) {
        this.size = size;
        this.publicKey = publicKey;
        this.file = file;
        prepareMetadata();
    }

    public void encrypt(String type) {
        encryptOnlyRSA();
    }

    private byte[] prepareMetadata() {
        return ByteBuffer.allocate(4).putInt(size).array();
    }

    private List<Object> encryptOnlyRSA() {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            String baseName = FilenameUtils.getBaseName(file.getPath());
            String extension = FilenameUtils.getExtension(file.getPath());
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 FileOutputStream fileOutputStream = new FileOutputStream(baseName + "_encrypted." + extension)) {
                fileOutputStream.write(prepareMetadata());
                byte[] inputBuffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(inputBuffer)) != -1) {
                    byte[] outputBuffer = cipher.update(inputBuffer, 0, length);
                    if(outputBuffer != null) fileOutputStream.write(outputBuffer);
                }
                byte[] outputBuffer = cipher.doFinal();
                if(outputBuffer != null) fileOutputStream.write(outputBuffer);
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

        return null;
    }
}
