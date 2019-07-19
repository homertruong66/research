package com.hoang.jerseyspringjdbc.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.http.HttpStatus;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse (ValidationException ex) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
        errorMessage.setMessage(ErrorMessage.APP_INVALID_PARAM);

        if ( ex instanceof ConstraintViolationException ) {
            ConstraintViolationException constraintException = (ConstraintViolationException) ex;
            errorMessage.setMessage(validateDetailsError(constraintException.getConstraintViolations()));
        }

        return Response.status(HttpStatus.BAD_REQUEST.value())
            .entity(errorMessage)
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

    private String validateDetailsError (Set<ConstraintViolation<?>> constraintViolations) {
        if ( constraintViolations == null || constraintViolations.size() == 0 ) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        constraintViolations.forEach(
            constraintViolation -> result.append(constraintViolation.getMessage()).append(".")
        );

        return result.toString();
    }
}