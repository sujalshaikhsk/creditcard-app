package com.strickers.creditcard.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strickers.creditcard.dto.AccountSummaryResponse;
import com.strickers.creditcard.dto.TransactionRequestDto;
import com.strickers.creditcard.dto.TransactionSummaryResponsedto;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.exception.CustomerNotFoundException;
import com.strickers.creditcard.exception.TransactionException;
import com.strickers.creditcard.service.LoginService;
import com.strickers.creditcard.service.TransactionService;
import com.strickers.creditcard.utils.ApiConstant;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/transactions")
@RestController
@Slf4j
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private LoginService creditCardloginService;
	
	@GetMapping("/{customerId}")
	public ResponseEntity<TransactionSummaryResponsedto> fetchTransactionsByMonth(
			@PathVariable("customerId") Long customerId, @RequestParam("month") String month)
			throws ParseException, TransactionException, CustomerNotFoundException {
		log.info("fetch fetchTransactionsByMonth() is called");
		List<AccountSummaryResponse> accountSummaryResponsedtoList = transactionService
				.fetchTransactionsByMonth(customerId, month);
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
	
	@PostMapping("")
	public ResponseEntity<Boolean> saveTransaction(@RequestBody TransactionRequestDto transactionRequestDto){
		log.info("Entering into transactions method of validateOtp in CreditCardController");
		Transaction transaction = creditCardloginService.saveTransaction(transactionRequestDto);
		if(!Objects.isNull(transaction)) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

}
