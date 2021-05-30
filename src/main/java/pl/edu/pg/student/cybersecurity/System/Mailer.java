package pl.edu.pg.student.cybersecurity.System;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mailer {

    private final String senderEmail = "messages@cybersecuriy.com";
    private final String senderUsername = "d90b586be0f868";
    private final String senderPassword = "fa94c366d35c35";

    private final String host = "smtp.mailtrap.io";
    private final String port = "2525";

    private String recipient;
    private File attachment;

    public Mailer(String recipient, File attachment) {
        this.recipient = recipient;
        this.attachment = attachment;
    }

    public boolean send(String from, String to) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", host);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", senderUsername);
        properties.put("mail.password", senderPassword);

        Authenticator authenticator = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderUsername, senderPassword);
            }
        };
        Session session = Session.getInstance(properties, authenticator);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("CyberSecurity - Message");
            BodyPart bodyPart = new MimeBodyPart();
            StringBuilder stringBuilder = new StringBuilder("<html>");
            stringBuilder.append("<h1>Hello " + to + "</h1>");
            stringBuilder.append("<hr>");
            stringBuilder.append("<h3>" + from + " sent you an encrypted message!</h3>");
            stringBuilder.append("</html>");
            bodyPart.setContent(stringBuilder.toString(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            bodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            bodyPart.setDataHandler(new DataHandler(source));
            bodyPart.setFileName(attachment.getName());
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
