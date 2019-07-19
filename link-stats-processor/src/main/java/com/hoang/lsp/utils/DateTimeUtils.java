package com.hoang.lsp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateTimeUtils {

    public static long AN_HOUR_IN_MILLIS = 3600000;

    private DateTimeUtils () {
    }

    private static final DateTimeFormatter yyyyMMddHH0000 = DateTimeFormat.forPattern("yyyyMMddHH0000");

    private static final ThreadLocal<SimpleDateFormat> ROUND_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue () {
            return new SimpleDateFormat("yyyyMMddHH0000");
        }

    };

    private static final ThreadLocal<SimpleDateFormat> ROUND_DATE_FORMAT_THREAD_LOCAL_WITHOUT_TIME = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue () {
            return new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        }

    };

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue () {
            return new SimpleDateFormat("yyyyMMddHH0000");
        }

    };

    private static final ThreadLocal<SimpleDateFormat> FILE_NAME_BY_DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue () {
            return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        }

    };

    public static String roundTimestampToEarliestHour (final Calendar calendar) {
        if ( calendar == null ) {
            throw new IllegalArgumentException("calendar can not null");
        }
        return ROUND_DATE_FORMAT_THREAD_LOCAL.get().format(calendar.getTime());
    }

    public static Calendar toCalendar (final String time) throws ParseException {
        if ( time == null ) {
            throw new IllegalArgumentException("time can not null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(yyyyMMddHH0000.parseDateTime(time).toDate());
        return cal;
    }

    public static Calendar toCalendarWithSecondTime (final String time) throws ParseException {
        if ( StringUtils.isEmpty(time) ) {
            throw new IllegalArgumentException("time can not null");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(NumberUtils.toLong(time, 0L) * 1000L));
        return cal;
    }

    public static String parseToString (final Calendar calendar) {
        if ( calendar == null ) {
            throw new IllegalArgumentException("calendar can not null");
        }
        return DATE_FORMAT_THREAD_LOCAL.get().format(calendar.getTime());
    }

    public static boolean isDateAnHour (final Calendar calendar) {
        if ( calendar == null ) {
            throw new IllegalArgumentException("calendar can not null");
        }

        long timeInMillis = calendar.getTimeInMillis();
        return (timeInMillis % AN_HOUR_IN_MILLIS) == 0;
    }

    public static String toStringWithTime (final Calendar calendar) {
        if ( calendar == null ) {
            throw new IllegalArgumentException("calendar can not null");
        }

        return FILE_NAME_BY_DATE_FORMAT_THREAD_LOCAL.get().format(calendar.getTime());
    }

    public static String parseCalendarWithoutTime (final String time) {
        Calendar calendar = Calendar.getInstance();
        if ( StringUtils.isNotBlank(time) ) {
            calendar.setTime(new Date(NumberUtils.toLong(time, 0L) * 1000L));
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return ROUND_DATE_FORMAT_THREAD_LOCAL_WITHOUT_TIME.get().format(calendar.getTime());
    }

}
