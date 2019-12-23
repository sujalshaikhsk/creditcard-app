package com.strickers.creditcard.utils;

public interface CreditcardValidator<T> {
	
	Boolean validate(T t);
}
