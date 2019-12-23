package com.strickers.creditcard.exception;

public class AgeNotMatchedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Customer above 18 years are eligible for credit card.";
	}
}
