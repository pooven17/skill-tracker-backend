package com.cts.skilltrkr.exception.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cts.skilltrkr.exception.ErrorResponse;
import com.cts.skilltrkr.exception.NotValidUpdateException;
import com.cts.skilltrkr.exception.ProfileNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	@ExceptionHandler({ MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class,
			HttpMessageNotReadableException.class, BindException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBadRequestException(Exception exception, WebRequest request) {
		log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), exception);
		String errorMsg = exception.getMessage();
		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exp = (MethodArgumentNotValidException) exception;
			List<String> msgList = new ArrayList<>();
			exp.getBindingResult().getAllErrors().forEach((error) -> {
				msgList.add(error.getDefaultMessage());
			});
			errorMsg = String.join(", ", msgList);
		} else if (exception.getMessage().contains("Expertise Level")) {
			errorMsg = "Expertise Level should be between 1 & 20";
		}

		return logAndBuildErrorResponse(HttpStatus.BAD_REQUEST, errorMsg, request.getDescription(false));
	}

	@ExceptionHandler(ProfileNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorResponse handleProfileNotFoundException(Exception exception, WebRequest request) {
		log.error(HttpStatus.NOT_FOUND.getReasonPhrase(), exception);
		return logAndBuildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request.getDescription(false));
	}

	@ExceptionHandler(NotValidUpdateException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleNotValidUpdateException(Exception exception, WebRequest request) {
		log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), exception);
		return logAndBuildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request.getDescription(false));
	}

	@ExceptionHandler(AuthenticationServiceException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorResponse handleAuthException(Exception exception, WebRequest request) {
		log.error(HttpStatus.FORBIDDEN.getReasonPhrase(), exception);
		return logAndBuildErrorResponse(HttpStatus.FORBIDDEN, exception.getMessage(),
				request.getDescription(false));
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleException(Exception exception, WebRequest request) {
		log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception);
		return logAndBuildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(),
				request.getDescription(false));
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
