package com.rms.rms.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyNumberUtil {

    /**
     * round doubles to n decimal places
     *
     * @param value
     * @param places
     * @return
     */
    public static Double roundDouble(Double value, Integer places) {
        if (value == null || places == null) {
            return null;
        }
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}