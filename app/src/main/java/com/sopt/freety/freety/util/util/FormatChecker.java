package com.sopt.freety.freety.util.util;

import java.util.regex.Pattern;

/**
 * Created by cmslab on 6/30/17.
 */

public class FormatChecker {

    // 정수인지 확인합시다!
    public static boolean isDecimal(String str) {
        try {
           int decimal = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isStringRanged(String str, int minLength, int maxLength) {
        int length = str.length();
        if (length < minLength || length > maxLength)
            return false;

        return true;
    }

    public static boolean isKorean(String str) {
        return Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str);
    }

    public static boolean isEmailFormat(String str) {
        return Pattern.matches("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", str);
    }
}
