/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.mails;

import com.plinepay.core.utils.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Component
public class EmailHtmlSender {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;

    public EmailStatus send(String to, String subject, String templateName, final Context context) {
        String body = templateEngine.process(templateName, context);
        return emailService.sendHtml(to, subject, body);
    }

    public EmailStatus send(
            final String to,
            final String subject,
            final String templateName,
            final String attachmentFileName,
            final byte[] attachmentBytes,
            final Context context) {
        String body = templateEngine.process(templateName, context);
        return emailService.sendHtmlWithAttachment(to, subject, body, attachmentFileName, attachmentBytes);
    }
}
