package com.food.foodorderapi.library.utils.NumberGenerator;


import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class OTPGenerator {

    private final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generate6DigitOtp() {
        int n = SECURE_RANDOM.nextInt(1_000_000); // 0..999999
        return String.format("%06d", n);
    }


}
