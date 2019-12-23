package com.strickers.creditcard.exception;

public class NotEligibleForCreditcardException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "Customer is not eligible for credit card.";
	}

}
