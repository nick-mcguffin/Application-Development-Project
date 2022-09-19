package com.wilma.service;

import com.wilma.service.mail.Mailer;
import com.wilma.web.App;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = App.class)
class MailerTest {

    @Autowired
    Mailer mailer;

    @Disabled
    @Test
    void sendEmailToSingleRecipient() {
        var to = "example@example.com";
        var subject = "Test Email";
        var text = "This is an automated test email from WILMA";
        assertNotNull(mailer.sendEmail(to, subject, text));
    }

    @Disabled
    @Test
    void sendEmailToMultipleRecipients() {
        var to = new String[]{"example@example.com", "example@example.net", "example@example.org", "example@example.edu"};
        var subject = "Test Email";
        var text = "This is an automated test email from WILMA";
        assertNotNull(mailer.sendEmail(to, subject, text));
    }

    @Disabled
    @Test
    void sendEmailWithAttachmentsToSingleRecipient() {
        var to = "example@example.com";
        var subject = "Test Email";
        var text = "This is an automated test email from WILMA with an attachment";
        var attachments = Map.of("willy-wonka.png", "src/test/resources/willy-wonka.png");
        assertNotNull(mailer.sendEmailWithAttachments(to, subject, text, attachments));
    }

    @Disabled
    @Test
    void sendEmailWithAttachmentsToMultipleRecipients() {
        var to = new String[]{"example@example.com", "example@example.net", "example@example.org", "example@example.edu"};
        var subject = "Test Email";
        var text = "This is an automated test email from WILMA with an attachment";
        var attachments = Map.of("willy-wonka.png", "src/test/resources/test-images/willy-wonka.png");
        assertNotNull(mailer.sendEmailWithAttachments(to, subject, text, attachments));
    }
}