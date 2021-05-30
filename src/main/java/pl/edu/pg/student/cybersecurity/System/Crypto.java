package pl.edu.pg.student.cybersecurity.System;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Crypto {

    protected File file;

    public Crypto(File file) {
        this.file = file;
    }

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

    protected String getFileName(Integer size, String mode, String type) {
        String baseName = FilenameUtils.getBaseName(file.getPath()).split("__")[0];
        String extension = FilenameUtils.getExtension(file.getPath());
        String path;
        if(type.equals("encrypted")) path = Paths.get("C:/CyberSecurity 1.0/Encrypted Files/").toString();
        else path = Paths.get("C:/CyberSecurity 1.0/Decrypted Files/").toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
        Date date = new Date();
        String dateString = simpleDateFormat.format(date);
        return Paths.get(path, baseName + "__" + size + "__" + mode + "__" + dateString + "__" + type + "." + extension).toString();
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
