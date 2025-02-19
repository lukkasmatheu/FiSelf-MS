package com.financial.self.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLoginCredentialsException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;
	
	private static final String DEFAULT_MESSAGE = "Invalid login credentials provided";

	public InvalidLoginCredentialsException() {
		super(HttpStatus.UNAUTHORIZED, DEFAULT_MESSAGE);
	}

}