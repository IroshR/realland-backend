package com.teamtrace.realland.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SimpleOTPGenerator {
    private SimpleOTPGenerator() {
    }

    public static String random(int size) {

        StringBuilder generatedToken = new StringBuilder();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < size; i++) {
                generatedToken.append(number.nextInt(9));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedToken.toString();
    }
}
