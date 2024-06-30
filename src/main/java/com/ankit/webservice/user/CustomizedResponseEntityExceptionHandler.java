package com.ankit.webservice.user;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest webRequest){
		ErrorDetails errorDetails = ErrorDetails.builder()
				.message(ex.getMessage())
				.timeStamp(LocalDateTime.now())
				.detail(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest webRequest){
		ErrorDetails errorDetails = ErrorDetails.builder()
				.message(ex.getMessage())
				.timeStamp(LocalDateTime.now())
				.detail(webRequest.getDescription(false))
				.build();
		return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorDetails errorDetails = ErrorDetails.builder()
				.message(ex.getFieldError().getDefaultMessage())
				.timeStamp(LocalDateTime.now())
				.detail(request.getDescription(false))
				.build();
		return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
