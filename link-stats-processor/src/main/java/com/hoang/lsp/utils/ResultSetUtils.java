package com.hoang.lsp.utils;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

public final class ResultSetUtils {

    private ResultSetUtils() {}

    public static BigInteger getBigInteger(ResultSet rs, String columnName) throws SQLException {
        return getValueByType(rs, columnName, BigInteger.class);
    }

    public static Long getLong(ResultSet rs, String columnName) throws SQLException {
        return getValueByType(rs, columnName, Long.class);
    }

    public static Integer getInt(ResultSet rs, String columnName) throws SQLException {
        return getValueByType(rs, columnName, Integer.class);
    }

    public static String getString(ResultSet rs, String columnName) throws SQLException {
        if (isColumnExists(rs, columnName)) {
            return rs.getString(columnName);
        }
        return null;
    }

    public static Calendar toCalendar(ResultSet rs, String columnName) throws SQLException {
        if (isColumnExists(rs, columnName)) {
            Timestamp ts = rs.getTimestamp(columnName);
            if (ts != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(ts.getTime());
                return calendar;
            }
        }
        return null;
    }

    public static boolean isColumnExists(ResultSet rs, String columnName) throws SQLException {
        boolean isExists = true;
        try {
            rs.findColumn(columnName);
        } catch (SQLException ex) {
            isExists = false;
        }
        return isExists;
    }

    private static <T> T getValueByType(ResultSet rs, String columnName, Class<T> classType) throws SQLException {
        final Object value = rs.getObject(columnName);
        if (value != null) {
            return classType.cast(value);
        }
        return null;
    }

}
