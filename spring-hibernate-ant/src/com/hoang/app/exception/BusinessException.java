package com.hoang.app.exception;

/**
 * 
 * @author Hoang Truong
 *
 */

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public BusinessException() {}

	public BusinessException(String message) {
		super(message);
	}
}
