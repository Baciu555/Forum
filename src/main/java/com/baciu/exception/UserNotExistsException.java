package com.baciu.exception;

public class UserNotExistsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserNotExistsException() {
		super();
	}
	
	public UserNotExistsException(String message) {
		super(message);
	}

}
