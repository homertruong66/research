package com.hoang.srj.controller.v1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthControllerV1Test {

    private static final String API_VERSION = "/v1";
    private static final String API_HEALTH  = "/health";

    @Autowired
    // TestRestTemplate is configured automatically when using the @SpringBootTestCase annotation,
    // and is configured to resolve relative paths to http://localhost:${local.server.port}.
    private TestRestTemplate restTemplate;

    @Test
    public void testCheckHealth_failed () {
//        ResponseEntity<String> errorMessageResponseEntity = restTemplate.getForEntity(
//            API_VERSION + API_HEALTH, String.class);
//
//        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorMessageResponseEntity.getStatusCode());
    }

    @Test
    public void testCheckHealth_ok () {
//        BDDMockito.given(modelSlaveTemplate.queryForObject(
//            BDDMockito.anyString(),
//            BDDMockito.eq(Integer.class)
//        )).willReturn(Integer.valueOf(1));

//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(API_VERSION + API_HEALTH, String.class);
//
//        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}