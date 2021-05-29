package pl.edu.pg.student.cybersecurity.System;

import org.apache.commons.io.FilenameUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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
    private String type;

    public Encryptor(Integer size, PublicKey publicKey, File file, String type) {
        this.size = size;
        this.publicKey = publicKey;
        this.file = file;
        this.type = type;
        prepareMetadata(type);
    }

    public void encrypt() {
        if(type.equals("RSA")) {
            encryptOnlyRSA();
        }
    }

    private byte[] prepareMetadata(String type) {
        byte[] metadata;
        if(type.equals("RSA")) {
            metadata = ByteBuffer.allocate(13)
                    .put("CSEDP".getBytes(StandardCharsets.UTF_8))
                    .putInt(size)
                    .putInt(0)
                    .array();
        } else {
            metadata = ByteBuffer.allocate(13 + (size / 8))
                    .put("CSEDP".getBytes(StandardCharsets.UTF_8))
                    .putInt(size)
                    .putInt(1)
                    .put(publicKey.getEncoded())
                    .array();
        }
        return metadata;
    }

    private List<Object> encryptOnlyRSA() {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            String baseName = FilenameUtils.getBaseName(file.getPath());
            String extension = FilenameUtils.getExtension(file.getPath());
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 FileOutputStream fileOutputStream = new FileOutputStream("./testing/" + baseName + "_encrypted." + extension)) {
                fileOutputStream.write(prepareMetadata(type));
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
