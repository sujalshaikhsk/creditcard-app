package com.strickers.creditcard.exception;

public class MobileNumberAlreadyExistException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Mobile Number Already exist.";
	}
}
