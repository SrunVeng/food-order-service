package com.food.foodorderapi.client.Gmail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.food.foodorderapi.library.utils.MailTemplateUtils.MailTemplates;
import com.food.foodorderapi.library.utils.NumberGenerator.TokenGenerator;

@Component
@RequiredArgsConstructor
@Slf4j
public class GmailClient {

    private final JavaMailSender mailSender;

    private static final String APP_NAME = "NHAM_EY";
    private static final String SUPPORT_EMAIL = "nhameyorder@gmail.com";
    private static final String COMPANY_NAME = "NHAM_EY Co., Ltd.";
    private static final String COMPANY_ADDR = "Phnom Penh";
    private static final String LOGO_URL = "https://res.cloudinary.com/dayrc0f7r/image/upload/v1759822292/nhamey_vzucm2.png";
    private static final String FROM = "No-Reply <no-reply@nhamey.example>";


    @Value("${web.portal.url}")
    private String WebPortalUrl;

    private void sendHtml(String to, String subject, String html) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        helper.setFrom(FROM);
        mailSender.send(mimeMessage);
    }

    public void sendConfirmationRegistered(String to) {
        if (!StringUtils.hasText(to)) return;

        // BUGFIX: previous code returned when email WAS valid. Reverse it.
        if (!EmailValidator.getInstance().isValid(to)) {
            log.warn("Not sending 'account created' email: invalid recipient '{}'", to);
            return;
        }

        String subject = "Your " + APP_NAME + " account is ready";
        String html = MailTemplates.accountCreatedEmail(
                APP_NAME,
                "", // pass display name if available
                "Open your dashboard",
                "https://camcoffee.example/app",
                SUPPORT_EMAIL,
                COMPANY_NAME,
                COMPANY_ADDR,
                LOGO_URL
        );

        try {
            sendHtml(to, subject, html);
        } catch (MessagingException ex) {
            log.error("Failed to send account-created email to {}", to, ex);
        }
    }

    public void sendOtpForRegister(String email, String code) {
        if (!StringUtils.hasText(email) || !EmailValidator.getInstance().isValid(email)) {
            log.warn("Not sending OTP: invalid recipient '{}'", email);
            return;
        }

        String subject = APP_NAME + " verification code: " + code;
        String html = MailTemplates.otpEmail(
                APP_NAME,
                code,
                SUPPORT_EMAIL,
                COMPANY_NAME,
                COMPANY_ADDR,
                LOGO_URL
        );
        try {
            sendHtml(email, subject, html);
            log.info("Sent OTP email to {}", email);
        } catch (MessagingException ex) {
            log.error("Failed to send OTP email to {}", email, ex);
        }
    }

    public void sendResetLink(String toEmail, String token) {
        if (!StringUtils.hasText(toEmail) || !EmailValidator.getInstance().isValid(toEmail)) {
            log.warn("Not sending reset link: invalid recipient '{}'", toEmail);
            return;
        }
        String resetUrl = WebPortalUrl + "/reset-password?token=" +
                URLEncoder.encode(token, StandardCharsets.UTF_8);

        String subject = "Reset your password";
        String html = MailTemplates.resetPasswordEmail(
                APP_NAME,
                resetUrl,
                SUPPORT_EMAIL,
                COMPANY_NAME,
                COMPANY_ADDR,
                LOGO_URL
        );
        try {
            sendHtml(toEmail, subject, html);
            log.info("Sent reset link to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send reset email to {}", toEmail, e);
            throw new RuntimeException("Failed to send reset email", e);
        }
    }
    public void sendAdminInvite(String toEmail, String invitedByDisplayName) {

        String token = TokenGenerator.generateToken();
        if (!StringUtils.hasText(toEmail) || !EmailValidator.getInstance().isValid(toEmail)) {
            log.warn("Not sending admin invite: invalid recipient '{}'", toEmail);
            return;
        }

        // Example target page in your React app; adjust path if needed:
        // e.g. /admin/set-password or /set-password
        String inviteUrl = WebPortalUrl + "/admin/set-password?token=" +
                URLEncoder.encode(token, StandardCharsets.UTF_8);

        String subject = APP_NAME + " admin access — set your password";

        String html = MailTemplates.adminInviteEmail(
                APP_NAME,
                invitedByDisplayName,   // who invited them (optional; can pass "" if unknown)
                inviteUrl,
                SUPPORT_EMAIL,
                COMPANY_NAME,
                COMPANY_ADDR,
                LOGO_URL
        );

        try {
            sendHtml(toEmail, subject, html);
            log.info("Sent admin invite to {}", toEmail);
        } catch (MessagingException ex) {
            log.error("Failed to send admin invite to {}", toEmail, ex);
            throw new RuntimeException("Failed to send admin invite", ex);
        }
    }
    public void sendRemoveConfirmationtoUser(String toEmail) { // keep exact name used by caller
        if (!StringUtils.hasText(toEmail) || !EmailValidator.getInstance().isValid(toEmail)) {
            log.warn("Not sending admin removal notice: invalid recipient '{}'", toEmail);
            return;
        }

        String subject = APP_NAME + " admin access removed";
        String html = MailTemplates.adminRemovedEmail(
                APP_NAME,
                SUPPORT_EMAIL,
                COMPANY_NAME,
                COMPANY_ADDR,
                LOGO_URL
        );

        try {
            sendHtml(toEmail, subject, html);
            log.info("Sent admin removal notice to {}", toEmail);
        } catch (MessagingException ex) {
            log.error("Failed to send admin removal notice to {}", toEmail, ex);
            // no rethrow to avoid masking delete operation result
        }
    }
}
