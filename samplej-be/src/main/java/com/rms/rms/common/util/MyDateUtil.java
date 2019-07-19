package com.rms.rms.common.util;


import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * homertruong
 */

public final class MyDateUtil {

    public static String dateFormat24h = "yyyy-MM-dd HH:mm:ss";

    public static Date addDate(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, amount);
        Date result = cal.getTime();

        return result;
    }

    public static Date convertToUTCDate (Date inputDate) {
        if (inputDate == null) {
            return null;
        }

        SimpleDateFormat utcSimpleDateFormat = new SimpleDateFormat(dateFormat24h);
        utcSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcDateString = utcSimpleDateFormat.format(inputDate);

        Date result = null;
        try {
            result = new SimpleDateFormat(dateFormat24h).parse(utcDateString);
        }
        catch (Exception ex) {}

        return result;
    }

    public static Date convertToUTCDate (String inputDateString) {
        if (StringUtils.isBlank(inputDateString)) {
            return null;
        }

        Date inputDate = null;
        try {
            inputDate = new SimpleDateFormat(dateFormat24h).parse(inputDateString);
        }
        catch (Exception ex) {}

        if (inputDate == null) {
            return null;
        }

        Date result = convertToUTCDate(inputDate);

        return result;
    }

    public static String getYYMMDDString () {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;      // 0 to 11
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return "" + year + "-" + (month > 9? month : "0" + month) + "-" + (day > 9? day : "0" + day);
    }

    public static String getYYMMDDString (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;      // 0 to 11
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return "" + year + "-" + (month > 9? month : "0" + month) + "-" + (day > 9? day : "0" + day);
    }

    public static ZonedDateTime toZonedDateTime (Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
    }

    public static String zonedDateTimeToISO8601 (ZonedDateTime zonedDateTime) {
        if ( zonedDateTime == null ) {
            return null;
        }
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

}
