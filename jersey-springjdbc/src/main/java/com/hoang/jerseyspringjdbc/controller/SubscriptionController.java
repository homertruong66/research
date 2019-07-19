package com.hoang.jerseyspringjdbc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoang.jerseyspringjdbc.exception.BusinessException;
import com.hoang.jerseyspringjdbc.service.UserService;
import com.hoang.jerseyspringjdbc.util.ResponseUtility;
import com.hoang.jerseyspringjdbc.view_model.SubscriptionCreateParam;
import com.hoang.jerseyspringjdbc.dto.SubscriptionDto;

public class SubscriptionController {

    public static final String RESPONSE_OBJECT_NAME  = "subscription";
    public static final String RESPONSE_OBJECTS_NAME = "subscriptions";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserService userService;

    @SuppressWarnings("unchecked")
    public Response create (int userId, @Valid SubscriptionCreateParam subscriptionCreateParam)
        throws IOException, BusinessException
    {
        SubscriptionDto subscriptionDto =
            userService.createSubscription(userId, subscriptionCreateParam.getSubscriptionCreateModel());
        Map<String, Object> requestMap = objectMapper.convertValue(subscriptionCreateParam, Map.class);
        requestMap.put("user_id", userId);

        return ResponseUtility.getOkResponse(requestMap, RESPONSE_OBJECT_NAME, subscriptionDto);
    }

    public Response get (int id) throws Exception {
        // request
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("id", id);

        // response
        SubscriptionDto subscriptionDto = userService.getSubscription(id);

        return ResponseUtility.getOkResponse(requestParams, RESPONSE_OBJECT_NAME, subscriptionDto);
    }
}
