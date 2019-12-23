package com.strickers.creditcard.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strickers.creditcard.dto.LoginRequestDto;
import com.strickers.creditcard.dto.LoginResponseDto;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.exception.LoginException;
import com.strickers.creditcard.repository.CustomerRepository;
import com.strickers.creditcard.utils.ApiConstant;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Autowired
	CustomerRepository customerRepository;

	/**
	 * @Description This method is used for user to login with valid credentials
	 * @param loginRequestdto
	 * @return LoginResponsedto
	 * @exception LOGIN_ERROR
	 */
	public Optional<LoginResponseDto> login(LoginRequestDto loginRequestdto) throws LoginException {
		log.info("Entering into login() method of LoginServiceImpl");
		Optional<Customer> customerResponse = customerRepository
				.findByMobileNumberAndPassword(loginRequestdto.getMobileNumber(), loginRequestdto.getPassword());
		if (!customerResponse.isPresent()) {
			log.error("Exception occured in login() method of LoginServiceImpl");
			throw new LoginException(ApiConstant.LOGIN_ERROR);
		}
		LoginResponseDto loginResponsedto = new LoginResponseDto();
		loginResponsedto.setCustomerID(customerResponse.get().getCustomerId());
		loginResponsedto.setCustomerName(customerResponse.get().getCustomerName());
		loginResponsedto.setType(ApiConstant.CREDIT_CARD_TYPE);
		return Optional.of(loginResponsedto);
	}
}
