package com.baciu.exception;

public class AccountBannedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AccountBannedException() {
		super();
	}

	public AccountBannedException(String message) {
		super(message);
	}
	
}
