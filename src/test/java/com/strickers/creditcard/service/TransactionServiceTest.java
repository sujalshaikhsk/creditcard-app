package com.strickers.creditcard.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.strickers.creditcard.dto.AccountSummaryResponse;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.exception.CustomerNotFoundException;
import com.strickers.creditcard.exception.TransactionException;
import com.strickers.creditcard.repository.CreditCardRepository;
import com.strickers.creditcard.repository.CustomerRepository;
import com.strickers.creditcard.repository.TransactionRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {
	
	@InjectMocks
	TransactionServiceImpl transactionServiceImpl;

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	CreditCardRepository creditCardRepository;
	
	Customer customer=null;
	CreditCard creditCard=null;
	List<Transaction> transactions=null;
	Transaction transaction=null;
	
	@Before
	public void before() {
		customer=new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("bindu");
		
		creditCard=new CreditCard();
		creditCard.setCustomer(customer);
		creditCard.setAvailableBalance(500.00);
		creditCard.setCreditCardNumber(12L);
		
		transactions=new ArrayList<>();
		transaction=new Transaction();
		transaction.setCreditCard(creditCard);
		transaction.setTransactionId(1);
		transaction.setTransactionAmount(50.0);
		transaction.setTransactionDate(LocalDateTime.of(2019, 05, 25, 12, 54));
		transactions.add(transaction);
	}
	@Test
	public void testFetchTransactionsByMonthForPositive() throws ParseException, TransactionException, CustomerNotFoundException {
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		Mockito.when(creditCardRepository.findByCustomer(customer)).thenReturn(creditCard);
		Mockito.when(transactionRepository.findByCreditCard(creditCard)).thenReturn(transactions);
		List<AccountSummaryResponse> response=transactionServiceImpl.fetchTransactionsByMonth(1L, "may", 2019);
		assertEquals(1, response.size());
	}
	
	@Test(expected = CustomerNotFoundException.class)
	public void testFetchTransactionsByMonthForNegativeCustomer() throws ParseException, TransactionException, CustomerNotFoundException {
		Optional<Customer> customerResponse = Optional.ofNullable(null);
		Mockito.when(customerRepository.findById(1L)).thenReturn(customerResponse);
		transactionServiceImpl.fetchTransactionsByMonth(1L, "may", 2019);
	}
	
	@Test(expected = TransactionException.class)
	public void testFetchTransactionsByMonthForNegativeTransaction() throws ParseException, TransactionException, CustomerNotFoundException {
		Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
		Mockito.when(creditCardRepository.findByCustomer(customer)).thenReturn(creditCard);
		List<Transaction> accountSummaryResponseList = new ArrayList<>();
		Mockito.when(transactionRepository.findByCreditCard(creditCard)).thenReturn(accountSummaryResponseList);
		transactionServiceImpl.fetchTransactionsByMonth(1L, "may", 2019);
		
		
	}
}
