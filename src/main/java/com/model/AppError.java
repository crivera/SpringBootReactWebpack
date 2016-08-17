package com.model;

import java.io.Serializable;

import org.json.JSONObject;

public class AppError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 
	 * @return
	 */
	public JSONObject toJSONObject() {
		return new JSONObject(this);
	}

	/**
	 * 
	 * @param code
	 * @param message
	 * @return
	 */
	public static AppError create(int code, String message) {
		AppError ae = new AppError();
		ae.setCode(code);
		ae.setMessage(message);
		return ae;
	}

}
