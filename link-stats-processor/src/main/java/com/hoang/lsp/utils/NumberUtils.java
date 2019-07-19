package com.hoang.lsp.utils;

import java.math.BigInteger;

public final class NumberUtils {

    public static final String NULL_STR = "null";

    private NumberUtils() {}

    public static Long toLong(final Integer number) {
        if (number == null) {
            return null;
        }
        return Long.valueOf(number);
    }

    public static BigInteger toBigInteger(final Long number) {
        if (number == null) {
            return null;
        }
        return BigInteger.valueOf(number);
    }

    public static BigInteger toBigInteger(final Integer number) {
        if (number == null) {
            return null;
        }
        return BigInteger.valueOf(number.longValue());
    }

    public static BigInteger convertToBigIntegerFromString(final String value) {
        if (value == null || value.equalsIgnoreCase(NULL_STR) || value.isEmpty()) {
            return null;
        }
        return new BigInteger(value);
    }

    public static Long convertToLongFromString(final String value) {
        if (value == null || value.equalsIgnoreCase(NULL_STR) || value.isEmpty()) {
            return null;
        }
        return Long.parseLong(value);
    }

    public static Integer convertToIntFromString(final String value) {
        if (value == null || value.equalsIgnoreCase(NULL_STR) || value.isEmpty()) {
            return null;
        }
        return Integer.valueOf(value);
    }

}
