package com.food.foodorderapi.library.utils.NumberGenerator;


import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.Base64;

@UtilityClass
public class TokenGenerator {

    private static final SecureRandom RNG = new SecureRandom();

    public String generateToken() {
        byte[] buf = new byte[32];
        RNG.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }
}
