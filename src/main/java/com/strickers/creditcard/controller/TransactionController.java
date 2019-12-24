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
import com.strickers.creditcard.exception.CustomerNotFoundException;
import com.strickers.creditcard.exception.TransactionException;
import com.strickers.creditcard.service.TransactionService;
import com.strickers.creditcard.utils.ApiConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * The TransactionController class fetches the list of transaction for the
 * particular year and month
 * 
 * @author Bindushree
 * 
 */
@RequestMapping("/transactions")
@RestController
@Slf4j
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	/**
	 * The fetchTransactionsByMonth method returns the list of transaction for the
	 * particular year and month
	 * 
	 * @author Bindushree
	 * 
	 * @param customerId
	 * @param month
	 * @param year
	 * @return TransactionSummaryResponsedto it returns the list of transaction for
	 *         the particular year and month
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws CustomerNotFoundException
	 */
	@GetMapping("/{customerId}")
	public ResponseEntity<TransactionSummaryResponsedto> fetchTransactionsByMonth(
			@PathVariable("customerId") Long customerId, @RequestParam("month") String month,
			@RequestParam("year") Integer year) throws ParseException, TransactionException, CustomerNotFoundException {
		log.info("Entering fetchTransactionsByMonth() method is called");
		List<AccountSummaryResponse> accountSummaryResponsedtoList = transactionService
				.fetchTransactionsByMonth(customerId, month, year);
		TransactionSummaryResponsedto transactionSummaryResponsedto = new TransactionSummaryResponsedto();
		if (accountSummaryResponsedtoList.isEmpty()) {
			log.error("Transaction list is empty");
			transactionSummaryResponsedto.setStatusCode(ApiConstant.FAILURE_CODE);
			transactionSummaryResponsedto.setMessage(ApiConstant.NO_TRANSACTIONS_FOUND);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
		log.info("Fetchig the list of transactions");
		transactionSummaryResponsedto.setTransactions(accountSummaryResponsedtoList);
		transactionSummaryResponsedto.setStatusCode(ApiConstant.SUCCESS_CODE);
		transactionSummaryResponsedto.setMessage(ApiConstant.SUCCESS);
		return new ResponseEntity<>(transactionSummaryResponsedto, HttpStatus.OK);
	}

}
