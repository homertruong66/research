package com.rms.rms.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.crypto.codec.Base64;

import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * homertruong
 */

public class MyTokenUtil {

    private static Logger logger = Logger.getLogger(MyTokenUtil.class);

    private static final String SEPARATOR = ",";
//    private static final String VALID_TOKEN_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\\\|;:\\'\\\",<.>/?";
    private static final String VALID_TOKEN_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static final String SLUG_PATTERN = "^[a-zA-Z0-9-_+]*$";
    public static final String DEFAULT_PASSWORD = "012345678";

    public static String convertToUrlFormat(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp)
                    .replaceAll("")
                    .toLowerCase()
                    .replaceAll(" ", "-")
                    .replaceAll("đ", "d");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    public static String[] decode(String token) {
        try {
            // Decode data on other side, by processing encoded data
            byte[] bytesDecoded = Base64.decode(token.getBytes());
            String rawString = new String(bytesDecoded);
            return rawString.split(SEPARATOR);
        }
        catch (Exception e) {
            logger.error(e);
        }

        return new String[] {};
    }

    public static String encode(String[] params) {
        try {
            // encode data on your side using BASE64
            String rawString = String.join(SEPARATOR, params);
            byte[] bytesEncoded = Base64.encode(rawString.getBytes());
            return new String(bytesEncoded);
        }
        catch (Exception e) {
            logger.error(e);
        }

        return "";
    }

    public static String generateRandomToken() {
        return RandomStringUtils.random(10, 0, VALID_TOKEN_CHAR.length(), false, false,
                VALID_TOKEN_CHAR.toCharArray(), new SecureRandom());
    }

    public static boolean isValidSlug(String slug) {
        if (StringUtils.isBlank(slug)) {
            return false;
        }

        return slug.matches(SLUG_PATTERN);
    }

}
