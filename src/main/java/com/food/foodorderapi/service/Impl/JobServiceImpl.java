package com.food.foodorderapi.service.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.food.foodorderapi.entity.EmailVerificationOTP;
import com.food.foodorderapi.repository.EmailVerificationOTPRepository;
import com.food.foodorderapi.repository.PasswordResetTokenRepository;
import com.food.foodorderapi.service.JobService;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final EmailVerificationOTPRepository  emailVerificationOTPRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void clean() {
        int rows = emailVerificationOTPRepository.deleteAllByStatuses(
                List.of(EmailVerificationOTP.Status.USED, EmailVerificationOTP.Status.EXPIRED)
        );
        log.info("Deleted email verification OTPs from DB total: {}", rows);

        passwordResetTokenRepository.expireAllOlderThan(Instant.now());
        log.info("Change Status passwordResetTokenRepository to expired");
        passwordResetTokenRepository.deleteAllFinished();
        log.info("Deleted passwordResetTokenRepository from DB expired");

    }
}
