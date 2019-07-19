package com.hoang.jerseyspringjdbc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.hoang.jerseyspringjdbc.dto.UserDto;
import com.hoang.jerseyspringjdbc.exception.BusinessException;
import com.hoang.jerseyspringjdbc.service.UserService;
import com.hoang.jerseyspringjdbc.util.ResponseUtility;
import com.hoang.jerseyspringjdbc.view_model.UserSearchCriteria;
import com.hoang.jerseyspringjdbc.view_model.UserCreateModel;
import jersey.repackaged.com.google.common.collect.ImmutableMap;

public class UserController {

    public static final String RESPONSE_OBJECT_NAME  = "user";
    public static final String RESPONSE_OBJECTS_NAME = "users";

    @Autowired
    protected UserService userService;

    public Response create (UserCreateModel userCreateModel) throws BusinessException {
        int id = userService.create(userCreateModel);
        return ResponseUtility.getOkResponse(
            ImmutableMap.of(),
            RESPONSE_OBJECT_NAME,
            ImmutableMap.of("id", Integer.valueOf(id))
        );
    }

    public Response get (int id) throws BusinessException {
        // request params
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("id", id);

        return ResponseUtility.getOkResponse(
            requestParams,
            RESPONSE_OBJECT_NAME,
            userService.get(id)
        );
    }

    public Response search (@Valid @BeanParam UserSearchCriteria criteria) throws BusinessException {
        // request
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("criteria", criteria);

        // response
        List<UserDto> userDtos = userService.search(criteria);
        return ResponseUtility.getOkResponse(
            requestParams,
            RESPONSE_OBJECTS_NAME,
            userDtos
        );
    }
}
