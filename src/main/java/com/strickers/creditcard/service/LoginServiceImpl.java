package com.strickers.creditcard.service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strickers.creditcard.dto.BuyRequestDto;
import com.strickers.creditcard.dto.LoginRequestDto;
import com.strickers.creditcard.dto.LoginResponseDto;
import com.strickers.creditcard.dto.TransactionRequestDto;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.entity.Otp;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.exception.LoginException;
import com.strickers.creditcard.repository.CreditCardRepository;
import com.strickers.creditcard.repository.CustomerRepository;
import com.strickers.creditcard.repository.OtpRepository;
import com.strickers.creditcard.repository.TransactionRepository;
import com.strickers.creditcard.utils.ApiConstant;
import com.strickers.creditcard.utils.Utils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private OtpRepository otpRepository;

	/**
	 * @Description This method is used for user to login with valid credentials
	 * @param loginRequestdto
	 * @return LoginResponsedto
	 * @exception LOGIN_ERROR
	 */
	public Optional<LoginResponseDto> login(LoginRequestDto loginRequestdto) throws LoginException {
		log.info("Entering into login() method of LoginServiceImpl");
		Optional<Customer> customerResponse = customerRepository.findByMobileNumber(loginRequestdto.getMobileNumber());
		if (!customerResponse.isPresent()) {
			log.error("Exception occured in login() method of LoginServiceImpl");
			throw new LoginException(ApiConstant.LOGIN_ERROR);
		}
		
		String decriptedPassword=Utils.decrypt(customerResponse.get().getPassword());
		log.error(loginRequestdto.getPassword()+"  ::::  "+decriptedPassword);

		if(!loginRequestdto.getPassword().equalsIgnoreCase(decriptedPassword)) {
			log.error("Exception occured in login() method of LoginServiceImpl");
			throw new LoginException(ApiConstant.LOGIN_ERROR);
		}
		
		LoginResponseDto loginResponsedto = new LoginResponseDto();
		loginResponsedto.setCustomerId(customerResponse.get().getCustomerId());
		loginResponsedto.setCustomerName(customerResponse.get().getCustomerName());
		loginResponsedto.setType(ApiConstant.CREDIT_CARD_TYPE);
		return Optional.of(loginResponsedto);
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

		Optional<CreditCard> creditcard = creditCardRepository.findById(buyRequestDto.getCreditCardNumber());
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

	/**
	 * This method is used for saving the shopping transaction
	 * @author Sujal
	 * @param transactionRequestDto
	 * @return Transaction
	 */
	@Transactional
	@Override
	public Transaction saveTransaction(TransactionRequestDto transactionRequestDto) {

		CreditCard creditcard=creditMoney(transactionRequestDto);
		if (!Objects.isNull(creditcard)) {
			Transaction transaction = new Transaction();
			transaction.setTransactionAmount(transactionRequestDto.getAmount());
			transaction.setRemarks("Buy Product");
			transaction.setTransactionDate(LocalDateTime.now());
			transaction.setCreditCard(creditcard);
			return transactionRepository.save(transaction);
		}
		return null;
	}

	/**
	 * 
	 * @param transactionRequestDto
	 * @return CreditCard
	 */
	@Transactional
	private synchronized CreditCard creditMoney(TransactionRequestDto transactionRequestDto) {
		Optional<CreditCard> OptionalCreditcard = creditCardRepository
				.findById(transactionRequestDto.getCreditCardNumber());
		if (OptionalCreditcard.isPresent()) {
			CreditCard creditCard = OptionalCreditcard.get();

			Double balance = creditCard.getAvailableBalance();
			creditCard.setAvailableBalance(balance - transactionRequestDto.getAmount());
			return creditCardRepository.save(creditCard);
		}
		return null;
	}
}
