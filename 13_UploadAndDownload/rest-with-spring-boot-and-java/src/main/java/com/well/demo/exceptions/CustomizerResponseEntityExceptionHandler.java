package com.well.demo.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@RestController
public class CustomizerResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionsResponse> handleAllExceptions(Exception ex, WebRequest request){
		
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(new Date(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionsResponse> handleNotFoundExceptions(Exception ex, WebRequest request){
		
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(new Date(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(RequiredObjectisNullException.class)
	public final ResponseEntity<ExceptionsResponse> handleBadRequestExceptions(Exception ex, WebRequest request){
		
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(new Date(), ex.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionsResponse> handleInvalidJwtAuthentication(Exception ex, WebRequest resquest){
		
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(new Date() ,ex.getMessage(), resquest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(MyFileNotFoundException.class)
	public final ResponseEntity<ExceptionsResponse> handleMyNotFoundException(Exception ex, WebRequest resquest){
		
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(new Date() ,ex.getMessage(), resquest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(FileStorageException.class)
	public final ResponseEntity<ExceptionsResponse> handleFileStorageException(Exception ex, WebRequest resquest){
		
		ExceptionsResponse exceptionResponse = new ExceptionsResponse(new Date() ,ex.getMessage(), resquest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
