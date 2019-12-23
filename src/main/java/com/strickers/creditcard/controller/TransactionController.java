package com.strickers.creditcard.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strickers.creditcard.dto.AccountSummaryResponse;
import com.strickers.creditcard.dto.TransactionSummaryResponsedto;
import com.strickers.creditcard.exception.TransactionException;
import com.strickers.creditcard.service.TransactionService;
import com.strickers.creditcard.utils.ApiConstant;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/transactions")
@RestController
@Slf4j
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping("/{creditCardNumber}")
	public ResponseEntity<TransactionSummaryResponsedto> fetchTransactionsByMonth(
			@PathVariable("creditCardNumber") Long creditCardNumber, @RequestParam("month") String month)
			throws ParseException, TransactionException {
		log.info("fetch fetchTransactionsByMonth() is called");
		List<AccountSummaryResponse> accountSummaryResponsedtoList = transactionService
				.fetchTransactionsByMonth(creditCardNumber, month);
		TransactionSummaryResponsedto transactionSummaryResponsedto=new TransactionSummaryResponsedto();
		if (accountSummaryResponsedtoList.isEmpty()) {
			transactionSummaryResponsedto.setStatusCode(ApiConstant.FAILURE_CODE);
			transactionSummaryResponsedto.setMessage(ApiConstant.NO_TRANSACTIONS_FOUND);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
		}
		transactionSummaryResponsedto.setTransactions(accountSummaryResponsedtoList);
		transactionSummaryResponsedto.setStatusCode(ApiConstant.SUCCESS_CODE);
		transactionSummaryResponsedto.setMessage(ApiConstant.SUCCESS);
		return new ResponseEntity<>(transactionSummaryResponsedto, HttpStatus.OK);
	}

}
