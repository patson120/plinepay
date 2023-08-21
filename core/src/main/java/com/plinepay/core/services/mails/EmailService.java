/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.mails;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.plinepay.core.utils.EmailStatus;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 *
 * @author Etapo
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public EmailStatus sendPlainText(String to, String subject, String text) {
        return sendM(to, subject, text, false);
    }

    public EmailStatus sendHtml(String to, String subject, String htmlBody) {
        return sendM(to, subject, htmlBody, true);
    }

    private EmailStatus sendM(String to, String subject, String text, Boolean isHtml) {
        try {
            MimeMessage mail = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            emailSender.send(mail);
            return new EmailStatus(to, subject, text).success();
        } catch (MessagingException | MailException e) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, e);
            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }

    public EmailStatus sendHtmlWithAttachment(
            final String to,
            final String subject,
            final String text,
            final String attachmentFileName,
            final byte[] attachmentBytes) {

        try {
            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = emailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
            message.setSubject(subject);
            message.setTo(to);
            message.setText(text, true);

            // Add the attachment
            final InputStreamSource attachmentSource = new ByteArrayResource(attachmentBytes);
            message.addAttachment(attachmentFileName, attachmentSource);

            // Send mail
            emailSender.send(mimeMessage);
            return new EmailStatus(to, subject, text).success();
        } catch (MessagingException | MailException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
            return new EmailStatus(to, subject, text).error(ex.getMessage());
        }
    }

}
