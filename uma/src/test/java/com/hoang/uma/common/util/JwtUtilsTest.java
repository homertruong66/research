package com.hoang.uma.common.util;

import com.hoang.uma.common.dto.UserDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * homertruong
 */

public class JwtUtilsTest {

    private JwtUtils clazzUnderTest = new JwtUtils();

    private String secretKey = "testSecretKey";

    private long expiration = 30000;

    private UserDto userDto;

    @Before
    public void setUp() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");

        userDto = new UserDto();
        userDto.setEmail("hoang@nt.com");
        userDto.setRoles(roles);

        ReflectionTestUtils.setField(clazzUnderTest, "secretKey", secretKey);
        ReflectionTestUtils.setField(clazzUnderTest, "expiration", expiration);
    }

    @Test
    public void testConstructor () throws Exception {
        Constructor<?> constructor = JwtUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testGenerateToken () {
        String token = clazzUnderTest.generateToken(userDto, new Date());

        Assert.assertNotNull(token);
    }

    @Test
    public void testGetEmail () {
        String token = clazzUnderTest.generateToken(userDto, new Date());
        String email = clazzUnderTest.getEmail(token);

        Assert.assertEquals(userDto.getEmail(), email);
    }

    @Test
    public void testGetExpirationDate() {
        Date now = new Date();
        String token = clazzUnderTest.generateToken(userDto, now);
        Date expirationDate = clazzUnderTest.getExpirationDate(token);

        Date expected = new Date(now.getTime() + expiration);
        Assert.assertTrue(expected.getTime() / 1000 == expirationDate.getTime() / 1000);
    }

    @Test
    public void testGetRoles () {
        String token = clazzUnderTest.generateToken(userDto, new Date());
        List<String> roles = clazzUnderTest.getRoles(token);

        Assert.assertNotNull(roles);
        Assert.assertEquals("ROLE_USER", roles.get(0));
        Assert.assertEquals("ROLE_ADMIN", roles.get(1));
    }

    @Test
    public void testValidateValidToken () {
        String token = clazzUnderTest.generateToken(userDto, new Date());
        userDto.setToken(token);

        Assert.assertTrue(clazzUnderTest.validateToken(token, userDto));
    }

    @Test
    public void testValidateWrongFormatToken () {
        String token = clazzUnderTest.generateToken(userDto, new Date());
        userDto.setToken(token);

        Assert.assertFalse(clazzUnderTest.validateToken("Wrong format token", userDto));
    }

    @Test
    public void testValidateTokenWithWrongEmail () {
        String token = clazzUnderTest.generateToken(userDto, new Date());

        userDto.setEmail("hoang1@nt.com");
        Assert.assertFalse(clazzUnderTest.validateToken(token, userDto));
    }

    @Test
    public void testValidateTokenWithExpiredToken () {
        ReflectionTestUtils.setField(clazzUnderTest, "expiration", 3000);
        String token = clazzUnderTest.generateToken(userDto, new Date());

        Assert.assertFalse(clazzUnderTest.validateToken(token, userDto));
    }

    @Test
    public void testValidateTokenWithWrongToken () {
        String token = clazzUnderTest.generateToken(userDto, new Date());
        userDto.setToken(token);

        ReflectionTestUtils.setField(clazzUnderTest, "expiration", 10000);
        String token1 = clazzUnderTest.generateToken(userDto, new Date());

        Assert.assertFalse(clazzUnderTest.validateToken(token1, userDto));
    }
}
