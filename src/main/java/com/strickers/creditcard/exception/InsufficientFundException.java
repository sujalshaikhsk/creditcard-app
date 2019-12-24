package com.strickers.creditcard.exception;

public class InsufficientFundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public InsufficientFundException(String s) {
		super(s);
	}


}
