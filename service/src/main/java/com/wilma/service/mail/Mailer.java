package com.wilma.service.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;

import javax.mail.internet.MimeMessage;
import java.util.Map;

public interface Mailer {
    MailMessage sendEmail(String to, String subject, String text) throws MailException;

    MailMessage sendEmail(String[] to, String subject, String text) throws MailException;

    MimeMessage sendEmailWithAttachments(String to, String subject, String text, Map<String, String> attachments);

    MimeMessage sendEmailWithAttachments(String[] to, String subject, String text, Map<String, String> attachments);
}
