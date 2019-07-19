package com.rms.rms.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * homertruong
 */

public class MyDateUtilTest {

    @Test
    public void testConstructor () throws Exception {
        Constructor<?> constructor = MyDateUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testZonedDateTimeToISO8601Ok () {
        String result = MyDateUtil.zonedDateTimeToISO8601(ZonedDateTime.now());
        Assert.assertNotNull(result);
    }

    @Test
    public void shouldZonedDateTimeToISO8601ReturnNull () {
        String result = MyDateUtil.zonedDateTimeToISO8601(null);
        Assert.assertNull(result);
    }

    @Test
    public void testToZonedDateTime () {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        ZonedDateTime result = MyDateUtil.toZonedDateTime(timestamp);
        Assert.assertNotNull(result);
    }

    @Test
    public void toZonedDateTimeShouldReturnNull () {
        ZonedDateTime result = MyDateUtil.toZonedDateTime(null);
        Assert.assertNull(result);
    }
}
