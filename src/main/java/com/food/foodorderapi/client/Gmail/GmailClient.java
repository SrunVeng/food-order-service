package com.food.foodorderapi.client.Gmail;

import com.food.foodorderapi.library.utils.MailTemplateUtils.MailTemplates;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;


@Component
@RequiredArgsConstructor
@Slf4j
public class GmailClient {

    private final JavaMailSender mailSender;


    private static final String APP_NAME = "NHAM_EY";
    private static final String SUPPORT_EMAIL = "NHAM_EY@food.example";
    private static final String COMPANY_NAME = "NHAM_EY Co., Ltd.";
    private static final String COMPANY_ADDR = "Phnom Penh";
    private static final String LOGO_URL = "https://yourcdn.example/logo-dark-on-light.png";
    private static final String FROM = "No-Reply <no-reply@camcoffee.example>"; // “no reply” sender


    private void sendMsgForRegistered(String to, String subject, String html) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        helper.setFrom(FROM);
        mailSender.send(mimeMessage);
    }

    public void sendMsgForRegistered(String to) {

        if (!StringUtils.hasText(to)) return;
        if (EmailValidator.getInstance().isValid(to)) return;

        String subject = "Your " + APP_NAME + " account is ready";
        String html = MailTemplates.accountCreatedEmail(
                APP_NAME,
                "", // or pass user’s display name if you have it
                "Open your dashboard",
                "https://camcoffee.example/app",
                SUPPORT_EMAIL,
                COMPANY_NAME,
                COMPANY_ADDR,
                LOGO_URL
        );

        try {
            sendMsgForRegistered(to, subject, html);
        } catch (MessagingException ex) {
            log.error("Failed to send account-created email to {}", to, ex);
        }
    }
}
