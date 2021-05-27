package pl.edu.pg.student.cybersecurity.System;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.util.List;

public class Decryptor {

    private PrivateKey privateKey;
    private File file;

    public Decryptor(PrivateKey privateKey, File file) {
        this.privateKey = privateKey;
        this.file = file;
    }

    public void decrypt() {
        readMetadata();
    }

    private List<Object> readMetadata() {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] metadata = new byte[4];
            fileInputStream.read(metadata, 0, 4);
            Integer size = ByteBuffer.wrap(metadata).getInt();
            System.out.println("Key size = " + size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Object> decryptOnlyRSA() {

        return null;
    }

    private List<Object> encryptAESRSA() {

        return null;
    }
}
