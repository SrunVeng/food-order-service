package com.food.foodorderapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port:587}")
    private int port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.default-encoding:UTF-8}")
    private String defaultEncoding;

    // nested properties under mail.properties.mail.smtp.*
    @Value("${mail.properties.mail.smtp.auth:true}")
    private boolean smtpAuth;

    @Value("${mail.properties.mail.smtp.starttls.enable:true}")
    private boolean starttlsEnable;

    // optional debug flag (defaults to false)
    @Value("${mail.debug:false}")
    private boolean debug;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setDefaultEncoding(defaultEncoding);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", String.valueOf(smtpAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(starttlsEnable));
        props.put("mail.debug", String.valueOf(debug));

        return sender;
    }
}
