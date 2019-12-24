package com.strickers.creditcard.service;

import com.strickers.creditcard.dto.BuyRequestDto;
import com.strickers.creditcard.dto.OtpRequestDto;
import com.strickers.creditcard.dto.OtpResponseDto;
import com.strickers.creditcard.exception.AccountNotFoundException;

public interface OtpService {
	
	public BuyRequestDto validateOtp(BuyRequestDto buyRequestDto);

	OtpResponseDto generateOtp(OtpRequestDto otpRequestDto) throws AccountNotFoundException;

}
