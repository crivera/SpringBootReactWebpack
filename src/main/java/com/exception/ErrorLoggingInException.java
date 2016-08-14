package com.exception;

public class ErrorLoggingInException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorLoggingInException(Exception e) {
		super(e);
	}

	public ErrorLoggingInException(String message) {
		super(message);
	}
}
