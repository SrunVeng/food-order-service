package com.food.foodorderapi.service.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.foodorderapi.entity.EmailVerificationOTP;
import com.food.foodorderapi.repository.EmailVerificationOTPRepository;
import com.food.foodorderapi.service.JobService;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final EmailVerificationOTPRepository  emailVerificationOTPRepository;

    @Override
    public void cleanOtp() {
        int rows = emailVerificationOTPRepository.deleteAllByStatuses(
                List.of(EmailVerificationOTP.Status.USED, EmailVerificationOTP.Status.EXPIRED)
        );
        log.info("Deleted email verification OTPs from DB total: {}", rows);
    }
}
