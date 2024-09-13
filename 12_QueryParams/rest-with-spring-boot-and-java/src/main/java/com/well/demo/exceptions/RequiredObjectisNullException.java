package com.well.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectisNullException extends AuthenticationException {
	
	private static final long serialVersionUID = 1L;
	

	public RequiredObjectisNullException() {
		super("It is not allowed number null !!!");
	}
	
	public RequiredObjectisNullException(String ex) {
		super(ex);
	}
	
}
