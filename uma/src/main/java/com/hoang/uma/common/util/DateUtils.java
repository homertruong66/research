package com.hoang.uma.common.util;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * homertruong
 */

public final class DateUtils {

    public static String zonedDateTimeToISO8601 (ZonedDateTime zonedDateTime) {
        if ( zonedDateTime == null ) {
            return null;
        }
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public static ZonedDateTime toZonedDateTime (Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }

        return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());
    }

}
