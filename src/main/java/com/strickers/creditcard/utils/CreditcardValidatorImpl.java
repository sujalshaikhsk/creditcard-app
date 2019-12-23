package com.strickers.creditcard.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.strickers.creditcard.dto.CreditcardRequestDto;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.exception.AgeNotMatchedException;
import com.strickers.creditcard.exception.NotEligibleForCreditcardException;
import com.strickers.creditcard.repository.CustomerRepository;

/**
 * 
 * @author Sujal
 * @description account validator is used to validate the customer whether the
 *              customer is eligible to take the credit card or not
 *
 */
@Component("creditcardValidator")
public class CreditcardValidatorImpl implements CreditcardValidator<CreditcardRequestDto> {

	@Autowired
	private CustomerRepository CustomerRepository;
	
	@Override
	public Boolean validate(CreditcardRequestDto creditcardRequestDto) {
		if (Utils.calculateAge(creditcardRequestDto.getDateOfBirth()) < StringConstant.MIN_AGE) {
			throw new AgeNotMatchedException();
		} else if (creditcardRequestDto.getSalary() < StringConstant.MIN_SALARY) {
			throw new NotEligibleForCreditcardException();
		} else if (isExist(creditcardRequestDto.getMobileNumber()))
		{
			throw new NotEligibleForCreditcardException();
		}
		return true;
	}

	private boolean isExist(Long mobileNumber) {
		Optional<Customer> customer =CustomerRepository.findByMobileNumber(mobileNumber);
		if(customer.isPresent())
			return true;
		return false;
	}

}
