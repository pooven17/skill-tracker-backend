package com.cts.skilltrkr.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleProfileNotFoundException(Exception exception, WebRequest request) {
        log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), exception);
        return logAndBuildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleException(Exception exception, WebRequest request) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception);
        return logAndBuildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getDescription(false));
    }

    private ErrorResponse logAndBuildErrorResponse(HttpStatus status, String message, String desc) {
        ErrorResponse response = new ErrorResponse();
        response.setStatusCode(status.value());
        response.setMessage(message);
        response.setTimestamp(new Date());
        response.setDescription(desc);
        return response;
    }
}