package com.baciu.exception;

public class UsernameExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameExistsException() {
		super();
	}
	
	public UsernameExistsException(String message) {
		super(message);
	}
}
