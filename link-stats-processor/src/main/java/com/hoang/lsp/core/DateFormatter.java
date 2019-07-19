package com.hoang.lsp.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class DateFormatter {

    private DateFormatter() {

    }

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_THREAD_LOCAL = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

    };

    public static DateFormat get() {
        return DATE_FORMAT_THREAD_LOCAL.get();
    }

}
