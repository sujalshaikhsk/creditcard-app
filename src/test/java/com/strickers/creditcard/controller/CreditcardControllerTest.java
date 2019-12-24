package com.strickers.creditcard.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strickers.creditcard.dto.CreditcardRequestDto;
import com.strickers.creditcard.dto.CreditcardResponseDto;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.service.CreditcardService;
import com.strickers.creditcard.service.LoginService;
import com.strickers.creditcard.utils.ApiConstant;

@RunWith(MockitoJUnitRunner.Silent.class)
@WebMvcTest(CreditCardController.class)
public class CreditcardControllerTest {

	@InjectMocks
	private CreditCardController creditCardController;

	@Mock
	private CreditcardService creditcardService;

	@Mock
	private LoginService loginService;
	
	private MockMvc mockMvc;

	CreditcardRequestDto creditcardRequestDto = null;
	CreditcardResponseDto creditcardResponseDto = null;
	CreditCard creditCard = null;
	Customer customer=null;

	@Before
	public void before() {
		creditcardRequestDto = new CreditcardRequestDto();
		creditcardRequestDto.setAddress("Address");
		creditcardRequestDto.setCustomerName("Sujal");
		creditcardRequestDto.setMobileNumber(9917818770L);
		creditcardRequestDto.setDateOfBirth(LocalDate.now());
		creditcardResponseDto = new CreditcardResponseDto();
		creditcardResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
		creditCard = new CreditCard();
		
		customer=new Customer();
		customer.setCustomerId(123L);
		
		creditCard.setCreditCardLimit(20000.00);
		creditCard.setAvailableBalance(2000.00);
		creditCard.setCreditCardNumber(123345L);
		creditCard.setValidFrom(LocalDate.now());
		creditCard.setValidTill(LocalDate.now());
		creditCard.setCustomer(customer);
	}

	
	@Test
	public void testCreateCreditCardForPositive() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.post("/creditcards")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(creditcardRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
		Mockito.when(creditcardService.createCreditCard(creditcardRequestDto)).thenReturn(Optional.of(creditCard));
		Mockito.verify(creditcardService).createCreditCard(creditcardRequestDto);
	}
	
	@Test
	public void testCreateCreditCardPositive() {
		Mockito.when(creditcardService.createCreditCard(creditcardRequestDto)).thenReturn(Optional.of(creditCard));
		Integer response = creditCardController.createCreditCard(creditcardRequestDto).getStatusCodeValue();
		assertEquals(200, response);
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  

}
