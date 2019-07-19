package com.hoang.linkshortener.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse (Throwable throwable) {
        LOGGER.error("", throwable);

        int errorStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorMessage.setMessage(ErrorMessage.APP_ERROR_MESSAGE);

        if ( throwable instanceof BusinessException ) {
            errorStatus = HttpStatus.UNPROCESSABLE_ENTITY.value();
            errorMessage.setCode(((BusinessException) throwable).getCode());
            errorMessage.setMessage(throwable.getMessage());
        }
        else if ( throwable instanceof WebApplicationException ) {
            errorStatus = ((WebApplicationException) throwable).getResponse().getStatus();
            errorMessage.setCode(((WebApplicationException) throwable).getResponse().getStatus());
            errorMessage.setMessage(ErrorMessage.APP_ERROR_MESSAGE);
        }
        else if ( throwable instanceof JsonProcessingException ) {
            errorStatus = HttpStatus.BAD_REQUEST.value();
            errorMessage.setCode(errorStatus);
            errorMessage.setMessage(((JsonProcessingException) throwable).getOriginalMessage());
        }

        return Response.
            status(errorStatus).
            entity(errorMessage).
            type(MediaType.APPLICATION_JSON_TYPE).
            build();
    }

}