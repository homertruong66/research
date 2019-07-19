package com.hoang.linkshortener.util;

import java.lang.reflect.Constructor;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void testConstructor () throws Exception {
        Constructor<?> constructor = DateUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testZonedDateTimeToISO8601Ok () {
        String parsing = DateUtils.zonedDateTimeToISO8601(ZonedDateTime.now());
        Assert.assertNotNull(parsing);
    }

    @Test
    public void shouldZonedDateTimeToISO8601ReturnNull () {
        String parsing = DateUtils.zonedDateTimeToISO8601(null);
        Assert.assertNull(parsing);
    }
}
