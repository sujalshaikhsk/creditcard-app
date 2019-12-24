package com.strickers.creditcard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.strickers.creditcard.dto.AccountSummaryResponse;
import com.strickers.creditcard.dto.TransactionSummaryResponsedto;
import com.strickers.creditcard.exception.CustomerNotFoundException;
import com.strickers.creditcard.exception.TransactionException;
import com.strickers.creditcard.service.TransactionService;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionControllerTest {

	@InjectMocks
	TransactionController transactionController;

	@Mock
	TransactionService transactionService;

	List<AccountSummaryResponse> accountSummaryResponsedtoList = null;
	AccountSummaryResponse accountSummaryResponse = null;
	TransactionSummaryResponsedto transactionSummaryResponsedto = null;

	@Before
	public void before() {
		accountSummaryResponsedtoList = new ArrayList<>();
		accountSummaryResponse = new AccountSummaryResponse();
		accountSummaryResponse.setCreditCardNumber(1L);
		accountSummaryResponse.setRemarks("fund");
		accountSummaryResponse.setTransactionAmount(200.0);
		accountSummaryResponse.setTransactionDate(LocalDateTime.now());
		accountSummaryResponsedtoList.add(accountSummaryResponse);

		transactionSummaryResponsedto = new TransactionSummaryResponsedto();
		transactionSummaryResponsedto.setStatusCode(200);
	}

	@Test
	public void testFetchTransactionsByMonthForPositive()
			throws ParseException, TransactionException, CustomerNotFoundException {
		Mockito.when(transactionService.fetchTransactionsByMonth(1L, "march", 2019))
				.thenReturn(accountSummaryResponsedtoList);
		Integer response = transactionController.fetchTransactionsByMonth(1L, "march", 2019).getStatusCodeValue();
		assertEquals(200, response);
	}

	@Test
	public void testFetchTransactionsByMonthForNegative()
			throws ParseException, TransactionException, CustomerNotFoundException {
		List<AccountSummaryResponse> accountSummaryResponsedto = new ArrayList<>();
		Mockito.when(transactionService.fetchTransactionsByMonth(1L, "march", 2019))
				.thenReturn(accountSummaryResponsedto);
		Integer response = transactionController.fetchTransactionsByMonth(1L, "march", 2019).getStatusCodeValue();
		assertEquals(404, response);
	}

}
