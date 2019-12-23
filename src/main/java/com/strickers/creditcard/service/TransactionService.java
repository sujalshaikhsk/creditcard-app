package com.strickers.creditcard.service;

import java.text.ParseException;
import java.util.List;

import com.strickers.creditcard.dto.AccountSummaryResponse;
import com.strickers.creditcard.exception.CustomerNotFoundException;
import com.strickers.creditcard.exception.TransactionException;

public interface TransactionService {
	
	List<AccountSummaryResponse> fetchTransactionsByMonth(Long creditCardNumber, String month) throws ParseException, TransactionException, CustomerNotFoundException;
	


}
