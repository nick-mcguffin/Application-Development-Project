package com.wilma.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.wilma.entity.users.ConfirmationToken;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service
public class MailerImpl implements Mailer {

    @Value("${spring.mail.site-email}")
    private String siteEmail;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public MailMessage sendEmail(String to, String subject, String text) throws MailException {
        return this.sendEmail(new String[]{to}, subject, text);
    }

    @Override
    public MailMessage sendEmail(String[] to, String subject, String text) throws MailException {
        var message = new SimpleMailMessage();
        message.setFrom(siteEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
        return message;
    }

    @Override
    public MimeMessage sendEmailWithAttachments(String to, String subject, String text, Map<String, String> attachments)  {
        return this.sendEmailWithAttachments(new String[]{to}, subject, text, attachments);
    }

    @Override
    public MimeMessage sendEmailWithAttachments(String[] to, String subject, String text, Map<String, String> attachments)  {
        try{
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setFrom(siteEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            attachments.forEach((filename, path) -> {
                try{
                    var file = new FileSystemResource(new File(path));
                    helper.addAttachment(filename, file);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

            mailSender.send(message);
            return message;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MailMessage sendConfirmationEmail(String to, ConfirmationToken confirmationToken) {
        
            var confirmationText = "To confirm your account, please click here: \n\n" +
                                    "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken();
            var message = new SimpleMailMessage();
            message.setFrom(siteEmail);
            message.setTo(to);
            message.setSubject("WILMA Confirmation Email");
            message.setText(confirmationText);

            mailSender.send(message);
            return message;

    }
}
