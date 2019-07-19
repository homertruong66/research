package com.hoang.jerseyspringjdbc.controller.v1;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoang.jerseyspringjdbc.controller.UserController;
import com.hoang.jerseyspringjdbc.dto.UserDto;
import com.hoang.jerseyspringjdbc.exception.BusinessException;
import com.hoang.jerseyspringjdbc.exception.ErrorMessage;
import com.hoang.jerseyspringjdbc.model.User;
import com.hoang.jerseyspringjdbc.util.DateUtils;
import com.hoang.jerseyspringjdbc.util.UserRowMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerV1Test {

    private static final String API_VERSION                 = "/v1";
    private static final String API_USERS_GET               = "/users/123";
    private static final String API_USERS_SEARCH            = "/users/search?name=hoang";
    private static final String JSON_RESPONSE_PROJECT_PATH  = "/response/" + UserController.RESPONSE_OBJECT_NAME;
    private static final String JSON_RESPONSE_PROJECTS_PATH = "/response/" + UserController.RESPONSE_OBJECTS_NAME;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    @Qualifier("modelSlaveTemplate")
    private JdbcTemplate modelSlaveTemplate;

    @MockBean
    @Qualifier("modelMasterNamedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate modelMasterNamedParameterJdbcTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnUserInfo () throws JsonProcessingException {
        ZonedDateTime now = ZonedDateTime.now();
        User user = new User();
        user.setId(1);
        user.setEmail("email");
        user.setName("name");
        user.setPassword("password");
        user.setLevel(1);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        BDDMockito.given(modelSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.any(Object[].class),
            BDDMockito.any(UserRowMapper.class)
        )).willReturn(user);

        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(
            API_VERSION + API_USERS_GET,
            JsonNode.class
        );

        JsonNode response = responseEntity.getBody();
        UserDto userDto = mapper.treeToValue(response.at(JSON_RESPONSE_PROJECT_PATH), UserDto.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals("email", userDto.getEmail());
        Assert.assertEquals("name", userDto.getName());
        Assert.assertEquals("password", userDto.getPassword());
        Assert.assertEquals(1, userDto.getLevel());
        Assert.assertEquals(DateUtils.zonedDateTimeToISO8601(now), userDto.getCreatedAt());
        Assert.assertEquals(DateUtils.zonedDateTimeToISO8601(now), userDto.getUpdatedAt());
    }

    @Test
    public void shouldThrowExceptionIfUserNotFound () {
        BDDMockito.given(modelSlaveTemplate.queryForObject(
            BDDMockito.anyString(),
            BDDMockito.any(Object[].class),
            BDDMockito.any(UserRowMapper.class)
        )).willThrow(new EmptyResultDataAccessException(1));

        ResponseEntity<ErrorMessage> responseEntity = restTemplate.getForEntity(
            API_VERSION + API_USERS_GET,
            ErrorMessage.class
        );
        ErrorMessage errorMessage = responseEntity.getBody();

        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        Assert.assertEquals(BusinessException.USER_NOT_FOUND, errorMessage.getCode());
        Assert.assertEquals("User doesn't exist!", errorMessage.getMessage());
    }

    @Test
    public void shouldSearchUsers () throws IOException {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId(1);
        user.setEmail("email1");
        user.setName("hoang");
        user.setPassword("password1");
        user.setLevel(1);
        user.setStatus("active");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        users.add(user);

        user = new User();
        user.setId(2);
        user.setEmail("email2");
        user.setName("hoang");
        user.setPassword("password2");
        user.setLevel(2);
        user.setStatus("active");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        users.add(user);

        BDDMockito.given(modelMasterNamedParameterJdbcTemplate.query(
            BDDMockito.anyString(),
            BDDMockito.any(MapSqlParameterSource.class),
            BDDMockito.any(UserRowMapper.class)
        )).willReturn(users);

        ResponseEntity<JsonNode> responseEntity = restTemplate.getForEntity(
            API_VERSION + API_USERS_SEARCH,
            JsonNode.class
        );

        JsonNode response = responseEntity.getBody();
        TypeReference<List<UserDto>> typeRef = new TypeReference<List<UserDto>>() {};
        List<UserDto> userDtos = mapper.readValue(response.at(JSON_RESPONSE_PROJECTS_PATH).traverse(), typeRef);
        for (int i = 0; i < userDtos.size(); i++) {
            UserDto userDto = userDtos.get(i);
            Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Assert.assertEquals("email" + (i + 1), userDto.getEmail());
            Assert.assertEquals("hoang", userDto.getName());
            Assert.assertEquals("password" + (i + 1), userDto.getPassword());
            Assert.assertEquals((i + 1), userDto.getLevel());
            Assert.assertEquals(DateUtils.zonedDateTimeToISO8601(now), userDto.getCreatedAt());
            Assert.assertEquals(DateUtils.zonedDateTimeToISO8601(now), userDto.getUpdatedAt());
        }
    }
}