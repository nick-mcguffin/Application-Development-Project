package com.wilma.service.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;

import com.wilma.entity.users.ConfirmationToken;

import javax.mail.internet.MimeMessage;
import java.util.Map;

public interface Mailer {
    /**
     * Send an email with no attachments to a single recipient
     * @param to The recipient email address
     * @param subject The email subject
     * @param text The email body text
     * @return A mail message
     * @throws MailException any number of exceptions that occur during message creation
     */
    MailMessage sendEmail(String to, String subject, String text) throws MailException;

    /**
     * Send an email with no attachments to one or more recipients
     * @param to The array of recipient email addresses
     * @param subject The email subject
     * @param text The email body text
     * @return A mail message
     * @throws MailException any number of exceptions that occur during message creation
     */
    MailMessage sendEmail(String[] to, String subject, String text) throws MailException;

    /**
     * Send an email with one or more attachments to a single recipient
     * @param to The recipient email address
     * @param subject The email subject
     * @param text The email body text
     * @param attachments A collection of attachments defined by their [filename, filepath>] key value pairs
     * @return A MIME type mail message
     * @throws MailException any number of exceptions that occur during message creation
     */
    MimeMessage sendEmailWithAttachments(String to, String subject, String text, Map<String, String> attachments);
    /**
     * Send an email with one or more attachments to one or more recipients
     * @param to The array of recipient email addresses
     * @param subject The email subject
     * @param text The email body text
     * @param attachments A collection of attachments defined by their [filename, filepath>] key value pairs
     * @return A MIME type mail message
     * @throws MailException any number of exceptions that occur during message creation
     */
    MimeMessage sendEmailWithAttachments(String[] to, String subject, String text, Map<String, String> attachments);

    /**
     * Send a confirmation email to a new users email address to finish the registration authentication process
     * @param to The email address of a new user account request
     * @param confirmationToken The token used to authenticate an email address to a new user account
     * @return a mail message
     */
    MailMessage sendConfirmationEmail(String to, ConfirmationToken confirmationToken);
}
