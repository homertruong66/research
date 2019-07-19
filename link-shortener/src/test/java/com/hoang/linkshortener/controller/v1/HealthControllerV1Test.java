package com.hoang.linkshortener.controller.v1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerV1Test {

    private static final String API_VERSION = "/v1";
    private static final String API_HEALTH  = "/healthcheck";
    @MockBean
    @Qualifier("modelSlaveTemplate")
    private JdbcTemplate     modelSlaveTemplate;
    @MockBean
    @Qualifier("modelMasterTemplate")
    private JdbcTemplate     modelMasterTemplate;
    @MockBean
    @Qualifier("sharesSlaveTemplate")
    private JdbcTemplate     sharesSlaveTemplate;
    @MockBean
    @Qualifier("consumptionsSlaveTemplate")
    private JdbcTemplate     consumptionsSlaveTemplate;
    @Autowired
    // TestRestTemplate is configured automatically when using the @SpringBootTestCase annotation,
    // and is configured to resolve relative paths to http://localhost:${local.server.port}.
    private TestRestTemplate restTemplate;

    @Test
    public void testCheckHealth_failed () {
        BDDMockito.given(modelSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willThrow(new RuntimeException());

        BDDMockito.given(modelMasterTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willThrow(new RuntimeException());

        BDDMockito.given(sharesSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willThrow(new RuntimeException());

        BDDMockito.given(consumptionsSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willThrow(new RuntimeException());

        ResponseEntity<String> errorMessageResponseEntity = restTemplate.getForEntity(
            API_VERSION + API_HEALTH, String.class);

        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorMessageResponseEntity.getStatusCode());
    }

    @Test
    public void testCheckHealth_ok () {
        BDDMockito.given(modelSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willReturn(Integer.valueOf(1));

        BDDMockito.given(modelMasterTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willReturn(Integer.valueOf(1));

        BDDMockito.given(sharesSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willReturn(Integer.valueOf(1));

        BDDMockito.given(consumptionsSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.eq(Integer.class)
        )).willReturn(Integer.valueOf(1));

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_VERSION + API_HEALTH, String.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}