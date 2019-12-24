package com.strickers.creditcard.service;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.strickers.creditcard.dto.CreditcardRequestDto;
import com.strickers.creditcard.dto.CreditcardResponseDto;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.repository.CreditCardRepository;
import com.strickers.creditcard.repository.CustomerRepository;
import com.strickers.creditcard.repository.TransactionRepository;
import com.strickers.creditcard.utils.ApiConstant;
import com.strickers.creditcard.utils.CreditcardValidator;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CreditcardServiceTest {

	@InjectMocks
	private CreditcardServiceImpl creditcardServiceImpl;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private CreditCardRepository creditCardRepository;

	@Mock
	private CreditcardValidator<CreditcardRequestDto> creditcardValidator;
	

	@Mock
	private RestTemplate restTemplate;

	Customer customer = null;
	CreditCard creditCard = null;
	List<Transaction> transactions = null;
	Transaction transaction = null;
	CreditcardRequestDto creditcardRequestDto = null;
	CreditcardResponseDto creditcardResponseDto = null;

	@Before
	public void before() {
		creditcardRequestDto = new CreditcardRequestDto();
		creditcardRequestDto.setAddress("Address");
		creditcardRequestDto.setCustomerName("Sujal");
		creditcardRequestDto.setMobileNumber(9917818770L);
		creditcardRequestDto.setDateOfBirth(LocalDate.now());
		creditcardResponseDto = new CreditcardResponseDto();
		creditcardResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);

		creditCard.setCreditCardLimit(20000.00);
		creditCard.setAvailableBalance(2000.00);
		creditCard.setCreditCardNumber(123345L);
		creditCard.setValidFrom(LocalDate.now());
		creditCard.setValidTill(LocalDate.now());
		creditCard.setCustomer(customer);
		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("bindu");

		creditCard = new CreditCard();
		creditCard.setCustomer(customer);
		creditCard.setAvailableBalance(500.00);
		creditCard.setCreditCardNumber(12L);

		transactions = new ArrayList<>();
		transaction = new Transaction();
		transaction.setCreditCard(creditCard);
		transaction.setTransactionId(1);
		transaction.setTransactionAmount(50.0);
		transaction.setTransactionDate(LocalDateTime.of(2019, 05, 25, 12, 54));
		transactions.add(transaction);
	}

	@Test
	public void testCreateCreditCardForPositive() {
		Mockito.when(creditcardValidator.validate(creditcardRequestDto)).thenReturn(true);
		Mockito.when(transactionRepository.findByCreditCard(creditCard)).thenReturn(transactions);
		Optional<CreditCard> response = creditcardServiceImpl.createCreditCard(creditcardRequestDto);
		assertEquals(creditCard.getCreditCardNumber(), response.get().getCreditCardNumber());
	}

	@Test
	public void testCreateCreditCardForNegative() {
		Mockito.when(creditcardValidator.validate(creditcardRequestDto)).thenReturn(false);
		Mockito.when(transactionRepository.findByCreditCard(creditCard)).thenReturn(transactions);
		Optional<CreditCard> response = creditcardServiceImpl.createCreditCard(creditcardRequestDto);
		assertEquals(creditCard.getCreditCardNumber(), response.get().getCreditCardNumber());
	}
	
	@Test
	public void testSaveCustomerForPositive() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<CreditcardRequestDto> entity = new HttpEntity<>(creditcardRequestDto, headers);

		String url = "http://localhost:8084/shoppingcart/customers";
		
		Mockito.when(restTemplate.exchange(url, HttpMethod.POST, entity, Customer.class).getBody()).thenReturn(customer);
		Customer customer1=creditcardServiceImpl.saveCustomer(creditcardRequestDto);
		assertEquals(customer.getCustomerId(), customer1.getCustomerId());
	}

	@Test
	public void testSaveCustomerForNegative() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<CreditcardRequestDto> entity = new HttpEntity<>(creditcardRequestDto, headers);

		String url = "http://localhost:8084/shoppingcart/customers";
		
		Mockito.when(restTemplate.exchange(url, HttpMethod.POST, entity, Customer.class).getBody()).thenReturn(null);
		Customer customer1=creditcardServiceImpl.saveCustomer(creditcardRequestDto);
		assertNull(customer1);
	}

}
