package com.hoang.uma.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * homertruong
 */

public class DateUtilsTest {

    @Test
    public void testConstructor () throws Exception {
        Constructor<?> constructor = DateUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testZonedDateTimeToISO8601Ok () {
        String result = DateUtils.zonedDateTimeToISO8601(ZonedDateTime.now());
        Assert.assertNotNull(result);
    }

    @Test
    public void shouldZonedDateTimeToISO8601ReturnNull () {
        String result = DateUtils.zonedDateTimeToISO8601(null);
        Assert.assertNull(result);
    }

    @Test
    public void testToZonedDateTime () {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        ZonedDateTime result = DateUtils.toZonedDateTime(timestamp);
        Assert.assertNotNull(result);
    }

    @Test
    public void toZonedDateTimeShouldReturnNull () {
        ZonedDateTime result = DateUtils.toZonedDateTime(null);
        Assert.assertNull(result);
    }
}
