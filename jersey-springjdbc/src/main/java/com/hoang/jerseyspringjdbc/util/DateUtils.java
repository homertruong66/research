package com.hoang.jerseyspringjdbc.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private DateUtils () {
    }

    public static String zonedDateTimeToISO8601 (ZonedDateTime zonedDateTime) {
        if ( zonedDateTime == null ) {
            return null;
        }
        return zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public static ZonedDateTime toZonedDateTime (ResultSet rs, String columnName) throws SQLException {
        if ( isColumnExists(rs, columnName) ) {
            Timestamp ts = rs.getTimestamp(columnName);
            if ( ts != null ) {
                ZonedDateTime dateTime = ZonedDateTime.ofInstant(ts.toInstant(), ZoneId.systemDefault());
                return dateTime;
            }
        }
        return null;
    }

    public static boolean isColumnExists (ResultSet rs, String columnName) throws SQLException {
        boolean isExists = true;
        try {
            rs.findColumn(columnName);
        }
        catch (SQLException ex) {
            isExists = false;
        }
        return isExists;
    }
}
