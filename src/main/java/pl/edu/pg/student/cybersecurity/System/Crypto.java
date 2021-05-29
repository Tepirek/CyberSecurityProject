package pl.edu.pg.student.cybersecurity.System;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Crypto {

    protected void processData(Cipher cipher, FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException, IllegalBlockSizeException, BadPaddingException {
        byte[] inputBuffer = new byte[1024];
        int length;
        while ((length = fileInputStream.read(inputBuffer)) != -1) {
            byte[] outputBuffer = cipher.update(inputBuffer, 0, length);
            if(outputBuffer != null) fileOutputStream.write(outputBuffer);
        }
        byte[] outputBuffer = cipher.doFinal();
        if(outputBuffer != null) fileOutputStream.write(outputBuffer);
    }

    public static void print(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (byte b : bytes) {
            sb.append(String.format("0x%02X ", b));
        }
        sb.append("]");
        System.out.printf("%s\n", sb);
    }
}
