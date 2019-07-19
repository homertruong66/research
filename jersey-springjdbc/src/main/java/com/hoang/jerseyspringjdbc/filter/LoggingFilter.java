package com.hoang.jerseyspringjdbc.filter;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.server.ContainerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void filter (ContainerRequestContext containerRequestContext) throws IOException {
        Map postRequestBody = null;
        if ( RequestMethod.POST.name().equals(containerRequestContext.getMethod())
             && containerRequestContext.hasEntity() ) {
            if ( containerRequestContext instanceof ContainerRequest ) {
                ContainerRequest request = (ContainerRequest) containerRequestContext;
                if ( request.hasEntity() ) {
                    request.bufferEntity();
                    postRequestBody = objectMapper.readValue(request.getEntityStream(), Map.class);
                }
            }
        }
        String requestLog = "HTTP REQUEST: {} {}" + System.lineSeparator() +
                            "Header: {}" + System.lineSeparator() +
                            "Request Params: {}" + System.lineSeparator() +
                            "Request Entity: {}" + System.lineSeparator();
        LOGGER.info(
            requestLog,
            containerRequestContext.getMethod(), containerRequestContext.getUriInfo().getPath(),
            containerRequestContext.getHeaders(),
            containerRequestContext.getUriInfo().getQueryParameters(),
            postRequestBody
        );
    }

    @Override
    public void filter (ContainerRequestContext containerRequestContext,
                        ContainerResponseContext containerResponseContext) throws IOException {
        String responseLog = "HTTP RESPONSE: {} {}" + System.lineSeparator() +
                             "Header: {}" + System.lineSeparator() +
                             "Response Entity: {}" + System.lineSeparator();
        LOGGER.info(
            responseLog,
            containerRequestContext.getMethod(), containerRequestContext.getUriInfo().getPath(),
            containerResponseContext.getHeaders(),
            objectMapper.writeValueAsString(containerResponseContext.getEntity())
        );
    }

}
