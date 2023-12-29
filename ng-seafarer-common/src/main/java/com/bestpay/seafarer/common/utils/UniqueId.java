package com.bestpay.seafarer.common.utils;

import org.apache.logging.log4j.util.Strings;

import java.util.Random;

/**
 * @Author: dengyancan
 * @Date: 2023/12/15 16:52
 */
public class UniqueId {

    private UniqueId() {}

    public static final int UNIQUE_CREDENTIAL_STR = 6;
    public static final int UNIQUE_CERTIFICATE = 6;

    public static String genRamCode(int strLength) {

        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'z', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return genString(strLength, str);
    }

    public static String genRamNumber(int strLength) {

        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return genString(strLength, str);
    }

    private static String genString(int strLength, char[] str) {

        int i;
        int count = 0;
        int maxNum = str.length;
        String currentTime = String.valueOf(System.currentTimeMillis())
                .replaceFirst(String.valueOf(str[1]), String.valueOf(str[2]));
        StringBuilder code = new StringBuilder(Strings.EMPTY);
        Random r = new Random();
        while (count < strLength) {
            i = Math.abs(r.nextInt(maxNum));
            if (i < maxNum) {
                code.append(str[i]);
                count++;
            }
        }
        return currentTime + code;
    }

    @SuppressWarnings("ALL")
    public static String uniqueCredentialStr() {
        return genRamCode(UNIQUE_CREDENTIAL_STR);
    }

    public static String uniqueCertificate() {
        return genRamNumber(UNIQUE_CERTIFICATE);
    }
}
