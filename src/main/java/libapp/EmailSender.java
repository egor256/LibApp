package libapp;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailSender
{
    private static JavaMailSenderImpl mailSender;

    public static JavaMailSenderImpl getSender()
    {
        return mailSender;
    }

    static
    {
        // Note: enable less secure app access: https://www.google.com/settings/security/lesssecureapps
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("library.app.2019");
        //mailSender.setPassword("*********"); // Enter mail sender password here!
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    public static void sendEmail(String recipientAddress, String subject, String message)
    {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }
}
