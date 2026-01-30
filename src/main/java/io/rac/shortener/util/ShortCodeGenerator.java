package io.rac.shortener.util;

import java.security.SecureRandom;
import java.util.Base64;

public class ShortCodeGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    private ShortCodeGenerator() {
        // Private constructor to hide the implicit public one
    }

    public static String generateShortCode() {
        byte[] randomBytes = new byte[6];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }
}
