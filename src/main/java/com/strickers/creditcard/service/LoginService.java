package com.strickers.creditcard.service;

import java.util.Optional;

import com.strickers.creditcard.dto.LoginRequestDto;
import com.strickers.creditcard.dto.LoginResponseDto;
import com.strickers.creditcard.dto.TransactionRequestDto;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.exception.LoginException;

public interface LoginService {
	public Optional<LoginResponseDto> login(LoginRequestDto loginRequestdto) throws LoginException;

	public Transaction saveTransaction(TransactionRequestDto transactionRequestDto);

}
