package com.hoang.linkshortener.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.hoang.linkshortener.controller.v1.HealthControllerV1;
import com.hoang.linkshortener.controller.v1.SubscriptionControllerV1;
import com.hoang.linkshortener.controller.v1.UserControllerV1;
import com.hoang.linkshortener.exception.GenericExceptionMapper;
import com.hoang.linkshortener.exception.ValidationExceptionMapper;
import com.hoang.linkshortener.filter.LoggingFilter;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig () {
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, Boolean.TRUE);

        // register validation exception mapper
        register(JacksonJaxbJsonProvider.class);
        register(ValidationExceptionMapper.class);

        // register exception mapper
        register(GenericExceptionMapper.class);

        // register filter
        register(LoggingFilter.class);

        // register controller here
        register(HealthControllerV1.class);
        register(UserControllerV1.class);
        register(SubscriptionControllerV1.class);
    }
}