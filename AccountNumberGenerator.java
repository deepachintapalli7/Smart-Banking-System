
package com.novara.util;

import java.security.SecureRandom;

public class AccountNumberGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateAccountNumber() {
        long number = 1_000_000_000L
                + RANDOM.nextLong(9_000_000_000L);

        return String.valueOf(number);
    }
}
