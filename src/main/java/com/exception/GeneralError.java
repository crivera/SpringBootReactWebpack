package com.exception;

public class GeneralError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GeneralError(Exception e) {
		super(e);
	}

	public GeneralError(String message) {
		super(message);
	}
}
