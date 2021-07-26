package com.zeneo.invoice;

import java.util.concurrent.ThreadLocalRandom;

public class StringUtil {

    public static String generateKey() {
        String key = "";
        key = key.concat(generateChar());
        key = key.concat(generateChar());
        key = key.concat(generateInt());
        key = key.concat(generateInt());
        key = key.concat(generateInt());
        key = key.concat(generateInt());

        return key;
    }

    private static String generateChar() {
        int r = ThreadLocalRandom.current().nextInt(64, 91);
        return String.valueOf(Character.toChars(r));
    }

    private static String generateInt() {
        int r = ThreadLocalRandom.current().nextInt(48, 58);
        return String.valueOf(Character.toChars(r));
    }

}
