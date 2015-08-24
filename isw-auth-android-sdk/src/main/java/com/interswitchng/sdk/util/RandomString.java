package com.interswitchng.sdk.util;

import java.util.Random;

/**
 * Created by crownus on 8/24/15.
 */
public class RandomString {
    private static final Random random = new Random();

    public static String numeric(int count) {
        if (count == 0) {
            return "";
        } else if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        int end = 'z' + 1;
        int start = ' ';
        char[] buffer = new char[count];
        int gap = end - start;
        while (count-- != 0) {
            char ch;
            ch = (char) (random.nextInt(gap) + start);
            if (Character.isDigit(ch)) {
                if (ch >= 56320 && ch <= 57343) {
                    if (count == 0) {
                        count++;
                    } else {
                        // low surrogate, insert high surrogate after putting it in
                        buffer[count] = ch;
                        count--;
                        buffer[count] = (char) (55296 + random.nextInt(128));
                    }
                } else if (ch >= 55296 && ch <= 56191) {
                    if (count == 0) {
                        count++;
                    } else {
                        // high surrogate, insert low surrogate before putting it in
                        buffer[count] = (char) (56320 + random.nextInt(128));
                        count--;
                        buffer[count] = ch;
                    }
                } else if (ch >= 56192 && ch <= 56319) {
                    // private high surrogate, no effing clue, so skip it
                    count++;
                } else {
                    buffer[count] = ch;
                }
            } else {
                count++;
            }
        }
        return new String(buffer);
    }
}
