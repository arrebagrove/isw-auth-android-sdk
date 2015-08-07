package com.interswitchng.sdk.util;

/**
 * Created by crownus on 8/6/15.
 */
public class StringUtils {
    public static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }
}
