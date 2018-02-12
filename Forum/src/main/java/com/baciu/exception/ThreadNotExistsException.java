package com.baciu.exception;

public class ThreadNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ThreadNotExistsException() {
		super();
	}
	
	public ThreadNotExistsException(String message) {
		super(message);
	}


}
