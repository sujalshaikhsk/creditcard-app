package com.strickers.creditcard.exception;

public class AccountNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public AccountNotFoundException(String s) {
		super(s);
	}


}
