package com.rms.rms.common.exception;

import org.apache.log4j.Logger;
import org.hibernate.QueryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

/**
 * homertruong
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody ErrorMessage handle(HttpServletRequest request,
                                             HttpServletResponse response, Exception ex)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
            errorMessage.setMessage("HttpRequestMethodNotSupportedException : " + ex.getMessage());
        }
        if (ex instanceof HttpMediaTypeNotSupportedException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
            errorMessage.setMessage("HttpMediaTypeNotSupportedException: " + ex.getMessage());
        }
        else if (ex instanceof HttpMessageNotReadableException) {
            logger.error("Error processing request param", ex);
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setMessage("Error processing request param: " + ex.getMessage());
        }
        else if ( ex instanceof BindException || ex instanceof ValidationException ||
                  ex instanceof MethodArgumentNotValidException || ex instanceof javax.validation.ValidationException ) {
            logger.error("Bind/ValidationException", ex);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
            errorMessage.setMessage("Bind/Validation Exception : " + ex.getMessage());
        }
        else if (ex instanceof AccessDeniedException) {
            logger.error("AccessDeniedException", ex);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            errorMessage.setCode(HttpStatus.FORBIDDEN.value());
            errorMessage.setMessage("AccessDeniedException: " + ex.getMessage());
        }
        else if (ex instanceof InvalidViewModelException) {
            logger.error("InvalidViewModelException", ex);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
            errorMessage.setMessage("InvalidViewModelException: " + ex.getMessage());
        }
        else if (ex instanceof BusinessException) {
            logger.error("BusinessException", ex);
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setCode(((BusinessException) ex).getCode());
            errorMessage.setMessage("BusinessException: " + ex.getMessage());
        }
        else if (ex instanceof QueryException) {
            logger.error("QueryException", ex);
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setMessage("QueryException : " + ex.getMessage());
        }
        else if (ex instanceof ObjectOptimisticLockingFailureException) {
            logger.error("OptimisticLockException", ex);
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setMessage("OptimisticLockException: " + ex.getMessage());
        }
        else {
            logger.error("Uncaught Exception", ex);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorMessage.setMessage("Uncaught Exception: " + ErrorMessage.APP_ERROR_MESSAGE);
        }

        return errorMessage;
    }
}
