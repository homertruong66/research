package com.hoang.uma.common.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
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
    public @ResponseBody ErrorMessage generalExceptionHandler(HttpServletRequest request,
                                                              HttpServletResponse response, Exception ex)
    {
        ErrorMessage errorMessage = new ErrorMessage();
        if (ex instanceof BindException || ex instanceof ValidationException) {
            logger.error("Bind/ValidationException", ex);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errorMessage.setCode(HttpStatus.BAD_REQUEST.value());
            errorMessage.setMessage("Bind/Validation Exception : " + ex.getMessage());
        }
        else if (ex instanceof AccessDeniedException) {
            logger.error("AccessDeniedException", ex);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            errorMessage.setCode(HttpStatus.FORBIDDEN.value());
            errorMessage.setMessage(ex.getMessage());
        }
        else if (ex instanceof BusinessException) {
            logger.error("BusinessException", ex);
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setMessage("Business Exception : " + ex.getMessage());
        }
        else if (ex instanceof ObjectOptimisticLockingFailureException) {
            logger.error("OptimisticLockException", ex);
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setMessage("Optimistic Lock Exception : " + ex.getMessage());
        }
        else {
            logger.error("Uncaught Exception", ex);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorMessage.setMessage("Uncaught Exception : " + ex.toString());
        }

        return errorMessage;
    }
}
