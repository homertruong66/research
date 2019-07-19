package com.hoang.linkredirector.util;

import java.util.Map;

import javax.ws.rs.core.Response;

import com.hoang.linkredirector.view_model.CustomResponseEntity;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import jersey.repackaged.com.google.common.collect.Maps;

public class ResponseUtility {

    public static final String REQUEST_INFO_OBJECT = "object";

    public static Response getOkResponse (Map<String, Object> requestParams, String objectName, Object responseObject) {
        CustomResponseEntity responseEntity = new CustomResponseEntity();
        // request
        Map<String, Object> request = Maps.newHashMap(requestParams);
        request.put(ResponseUtility.REQUEST_INFO_OBJECT, objectName);
        responseEntity.setRequest(request);
        // response
        responseEntity.setResponse(ImmutableMap.of(objectName, responseObject));
        return Response.ok(responseEntity).build();
    }
}
