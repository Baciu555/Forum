package com.baciu.exception;

public class FileUploadException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileUploadException() {
		super();
	}
	
	public FileUploadException(String message) {
		super(message);
	}
}
