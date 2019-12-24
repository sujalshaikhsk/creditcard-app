package com.strickers.creditcard.exception;

public class CvvDoesNotMatchException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public CvvDoesNotMatchException(String s) {
		super(s);
	}


}
