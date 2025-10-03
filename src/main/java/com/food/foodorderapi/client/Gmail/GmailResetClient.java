package com.food.foodorderapi.client.Gmail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@Slf4j
public class GmailResetClient {

    private final JavaMailSender mailSender;


    public void sendMsgForOTP(String email, String otp) {
    }
}
