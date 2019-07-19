package com.rms.rms.common.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * homertruong
 */

public class MyStringUtil {

    /**
     * compare unicode string
     *
     * @param source
     * @param destination
     * @return
     */
    public static boolean compareIgnoreAccent(String source, String destination){
        return removeAccent(source).equals(removeAccent(destination));
    }

    public static boolean isEmailFormatCorrect(String emailStr) {
        if (emailStr == null) {
            return false;
        }

        boolean result = true;
        try {
            new InternetAddress(emailStr);
        }
        catch (AddressException ex){
            result = false;
        }

        return result;
    }

    /**
     * Remove accent from vietnamese text
     *
     * @param text
     * @return
     */
    public static String removeAccent(String text) {
        String temp = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp)
                .replaceAll("")
                .replace('đ','d')
                .replace('Đ','D');
    }

    public static String toDecimalString(Double money, String pattern) {
        NumberFormat formatter = new DecimalFormat(pattern);

        return formatter.format(money);
    }
}
