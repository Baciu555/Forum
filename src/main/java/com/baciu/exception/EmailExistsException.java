package com.baciu.exception;

public class EmailExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailExistsException() {
		super();
	}
	
	public EmailExistsException(String message) {
		super(message);
	}
}
