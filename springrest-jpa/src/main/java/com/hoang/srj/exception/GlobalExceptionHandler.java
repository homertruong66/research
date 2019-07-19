package com.hoang.srj.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public @ResponseBody ErrorMessage defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        logger.error("General Exception", ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(500);
        errorMessage.setMessage("Uncaught Exception : " + ex.toString());

        return errorMessage;
    }

    @ExceptionHandler(value={BusinessException.class, BindException.class, ValidationException.class})
    public @ResponseBody ErrorMessage businessExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex){
        ErrorMessage errorMessage = new ErrorMessage();
        if (ex instanceof BindException || ex instanceof ValidationException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            errorMessage.setCode(0);
            errorMessage.setMessage("Bind/Validation Exception : " + ex.getMessage());
        }
        else {
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            errorMessage.setCode(1);
            errorMessage.setMessage("Business Exception : " + ex.getMessage());
        }

        return errorMessage;
    }
}
