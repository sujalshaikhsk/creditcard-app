package com.strickers.creditcard.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.strickers.creditcard.dto.BuyRequestDto;
import com.strickers.creditcard.dto.OtpRequestDto;
import com.strickers.creditcard.dto.OtpResponseDto;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Otp;
import com.strickers.creditcard.exception.AccountNotFoundException;
import com.strickers.creditcard.exception.CvvDoesNotMatchException;
import com.strickers.creditcard.repository.CreditCardRepository;
import com.strickers.creditcard.repository.OtpRepository;
import com.strickers.creditcard.utils.ApiConstant;
import com.strickers.creditcard.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private CreditCardRepository creditcardRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public OtpResponseDto generateOtp(OtpRequestDto otpRequestDto) throws AccountNotFoundException {

		Optional<CreditCard> creditcardDetails = creditcardRepository.findById(otpRequestDto.getCreditCardNumber());
		if (!creditcardDetails.isPresent()) {
			throw new AccountNotFoundException("Please enter valid account number");
		}
		
		if(!creditcardDetails.get().getCvv().equals(otpRequestDto.getCvv())) {
			throw new CvvDoesNotMatchException("Please enter valid cvv number");
		}
		Integer otp = Utils.generateOtp();
		Otp otpobj = new Otp();
		otpobj.setOtpNumber(otp);
		otpobj.setCreditCard(creditcardDetails.get());
		otpRepository.save(otpobj);

		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo(creditcardDetails.get().getCustomer().getEmail());
		simple.setFrom(ApiConstant.CREDITCARD_FROM_GMAILID);
		simple.setSubject(ApiConstant.CREDITCARD_GMAIL_SUBJECT);
		simple.setText(ApiConstant.CREDITCARD_TEXT_ONE + ApiConstant.NEXT_LINE + ApiConstant.CREDITCARD_TEXT_TWO
				+ ApiConstant.NEXT_LINE + ApiConstant.CREDITCARD_TEXT_THREE + otp + ApiConstant.NEXT_LINE
				+ ApiConstant.CREDITCARD_TEXT_FOUR);
		javaMailSender.send(simple);

		return new OtpResponseDto();
	}
	
	/**
	 * This method is used for validate the otp for customer
	 * @author Sujal
	 * @param buyRequestDto
	 * @return BuyRequestDto
	 */
	@Override
	public BuyRequestDto validateOtp(BuyRequestDto buyRequestDto) {
		BuyRequestDto buyRequestDto1 = new BuyRequestDto();

		Optional<CreditCard> creditcard = creditcardRepository.findById(buyRequestDto.getCreditCardNumber());
		if (creditcard.isPresent()) {
			log.error("inside creditcard present ");
			Otp otp = otpRepository.getOtpbyCreditCardNumber(creditcard.get().getCreditCardNumber());
			if (!Objects.isNull(otp)) {
				log.error("inside otp present ");
				buyRequestDto1.setOtp(otp.getOtpNumber());
				buyRequestDto1.setCreditCardNumber(creditcard.get().getCreditCardNumber());
				buyRequestDto1.setShippingAddress(buyRequestDto.getShippingAddress());
				return buyRequestDto1;
			}
		}
		return null;
	}

}
