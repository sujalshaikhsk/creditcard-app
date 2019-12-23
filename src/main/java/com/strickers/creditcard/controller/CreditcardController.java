package com.strickers.creditcard.controller;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strickers.creditcard.dto.BuyRequestDto;
import com.strickers.creditcard.dto.LoginRequestDto;
import com.strickers.creditcard.dto.LoginResponseDto;
import com.strickers.creditcard.dto.TransactionRequestDto;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.exception.LoginException;
import com.strickers.creditcard.service.LoginService;
import com.strickers.creditcard.utils.ApiConstant;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/creditcards")
@RestController
@Slf4j
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class CreditCardController {
	@Autowired
	LoginService creditCardloginService;

	/**
	 * 
	 * @param loginRequestdto
	 * @return
	 * @throws LoginException
	 */
	@PostMapping("/login")
	public ResponseEntity<Optional<LoginResponseDto>> creditCardLogin(@RequestBody LoginRequestDto loginRequestdto)
			throws LoginException {
		log.info("Entering into login method of creditCardLogin in CreditCardController");
		Optional<LoginResponseDto> loginResponsedto = creditCardloginService.login(loginRequestdto);
		if (!loginResponsedto.isPresent()) {
			LoginResponseDto loginResponse = new LoginResponseDto();
			loginResponse.setMessage(ApiConstant.LOGIN_ERROR);
			loginResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(Optional.of(loginResponse), HttpStatus.NOT_FOUND);
		}
		loginResponsedto.get().setMessage(ApiConstant.LOGIN_SUCCESS);
		loginResponsedto.get().setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(loginResponsedto, HttpStatus.OK);

	}
	
	@PostMapping("/otp")
	public ResponseEntity<BuyRequestDto> validateOtp(@RequestBody BuyRequestDto buyRequestDto){
		log.info("Entering into validateOtp method of validateOtp in CreditCardController");
		BuyRequestDto buyRequestDto1 = creditCardloginService.validateOtp(buyRequestDto);
		if(!Objects.isNull(buyRequestDto1))
			return new ResponseEntity<>(buyRequestDto1, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}
	
	@PostMapping("/transactions")
	public ResponseEntity<Boolean> saveTransaction(@RequestBody TransactionRequestDto transactionRequestDto){
		log.info("Entering into transactions method of validateOtp in CreditCardController");
		Transaction transaction = creditCardloginService.saveTransaction(transactionRequestDto);
		if(!Objects.isNull(transaction)) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}
	
}
